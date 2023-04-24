import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { BookingService } from 'src/app/services/booking.service';
import { FlightSearchService } from 'src/app/services/flight-search.service';

@Component({
  selector: 'app-checkout-success',
  templateUrl: './checkout-success.component.html',
  styleUrls: ['./checkout-success.component.scss']
})
export class CheckoutSuccessComponent implements OnInit, OnDestroy {

  bookingRef?: any;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private flightSvc: FlightSearchService,
    private bookingSvc: BookingService
  ) { }

  ngOnInit(): void {
    this.bookingRef = localStorage.getItem('bookingRef');

    setTimeout(() => {
      localStorage.removeItem('bookingRef');
      this.router.navigate(['/']);
    }, 10000);
  }

  ngOnDestroy(): void {
    localStorage.removeItem('bookingRef');
  }
  
}
