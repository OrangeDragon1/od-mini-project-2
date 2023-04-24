import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FlightSearchService } from '../services/flight-search.service';
import { Order } from '../models/order-models';
import { User } from '../models/common.models';
import { UserService } from '../services/user.service';
import { ConfirmationService, MessageService } from 'primeng/api';

@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.scss']
})
export class BookingComponent implements OnInit, OnDestroy {

  order?: Order;
  user?: User;
  buttonDisabled = true;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private flightSvc: FlightSearchService,
    private userSvc: UserService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) { }

  ngOnInit(): void {
    let token = localStorage.getItem('token');
    if (token) {
      this.userSvc.getUser(token)
        .then(results => {
          this.user = results;
          this.buttonDisabled = false;
        })
        .catch(error => {
          this.buttonDisabled = true;
        })
    }
    let bookingRef = this.activatedRoute.snapshot.params['ref'];
    this.flightSvc.getOrderByBookingRef(bookingRef)
    .then(results => {
      this.order = results;
    })
    .catch(errors => {
    })

  }

  confirm(event: Event) {
    this.confirmationService.confirm({
        target: event.target as any,
        message: 'Are you sure that you want to proceed?',
        icon: 'pi pi-exclamation-triangle',
        accept: () => {
            this.cancelBooking();
        },
        reject: () => {
            //reject action
        }
    });
}

  ngOnDestroy(): void {
    this.buttonDisabled = true;
  }

  cancelBooking() {
    let token = localStorage.getItem('token');
    if (token) {
      this.userSvc.getUser(token)
        .then(results => {
          this.user = results;
          this.buttonDisabled = false;
        })
        .catch(error => {
          this.buttonDisabled = true;
        })
    }
    let bookingRef = this.order?.bookingReference;
    this.flightSvc.deleteOrder(bookingRef, token)
    .then(results => {
      this.messageService.add({severity:'success', summary: 'Success', detail: 'Booking cancelled, redirecting you in 5 seconds'});
    setTimeout(() => {
      this.router.navigate(['/']);
    }, 5000);
    setTimeout(() => {
      location.reload();
    }, 5100);
    })
    .catch(err => {
      this.messageService.add({severity:'error', summary: 'Error', detail: 'Booking could not be cancelled'});
    });



    
  }
}
