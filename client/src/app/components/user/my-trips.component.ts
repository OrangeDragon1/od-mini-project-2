import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { User } from 'src/app/models/common.models';
import { Order } from 'src/app/models/order-models';
import { FlightSearchService } from 'src/app/services/flight-search.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-my-trips',
  templateUrl: './my-trips.component.html',
  styleUrls: ['./my-trips.component.scss']
})
export class MyTripsComponent implements OnInit {

  user?: User;
  orders?: Order[] = [];

  constructor(
    private router: Router,
    private userSvc: UserService,
    private activatedRoute: ActivatedRoute,
    private flightSvc: FlightSearchService
  ) { }

  ngOnInit(): void {
    let token = localStorage.getItem('token');
    if (token) {
      this.userSvc.getUser(token)
      .then(results => {
          this.user = results;
      })
      .catch(error => {
        if (error.status == 403) {
          localStorage.removeItem('token');
          this.router.navigate(['/']);
          setTimeout(() => {
            location.reload();
          }, 100);
        }
      })
    } else {
      this.router.navigate(['/']);
    }

    this.flightSvc.getAllOrdersByUserId(token)
    .then(results => {
      this.orders = results;
      // console.log(this.orders)
    })
  }

  onClick(event: Order) {
    // console.log(event)
    this.router.navigate(['/booking', event.bookingReference]);
  }
}
