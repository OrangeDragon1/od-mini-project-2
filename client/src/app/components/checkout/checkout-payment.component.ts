import { Component, OnDestroy, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Subject, Subscription } from 'rxjs';
import { PartialOfferOffer } from 'src/app/models/partial-offer.models';
import { BookingService } from 'src/app/services/booking.service';
import { CheckoutService } from 'src/app/services/checkout.service';
import { FlightSearchService } from 'src/app/services/flight-search.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-checkout-payment',
  templateUrl: './checkout-payment.component.html',
  styleUrls: ['./checkout-payment.component.scss']
})
export class CheckoutPaymentComponent implements OnInit, OnDestroy{

  @Output() bookingRef = new Subject<any>();

  sub$!: Subscription

  passengers?: FormArray;
  selectedOffer?: PartialOfferOffer;
  order!: FormGroup;

  loading = false;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private flightSvc: FlightSearchService,
    private fb: FormBuilder,
    private checkoutSvc: CheckoutService,
    private messageService: MessageService,
    private userSvc: UserService,
    private bookingSvc: BookingService
  ) { }

  ngOnInit(): void {
    let token = localStorage.getItem('token');
    if (token) {
        this.userSvc.getUser(token)
        .catch(error => {
          if (error.status == 403) {
            localStorage.removeItem('token');
            this.router.navigate(['/']);
            setTimeout(() => {
              location.reload();
            }, 1000);
          }
        })
    }

    let prq = this.activatedRoute.parent?.snapshot.params['prq'];
    let po = this.activatedRoute.parent?.snapshot.params['po'];
    let off = this.activatedRoute.parent?.snapshot.params['off'];

    const data = {
      partialOfferRequestId: prq,
      selectedPartialOffer: po
    }

    this.sub$ = this.checkoutSvc.data.subscribe(results => {
      if (results.value.length == 0) {
        this.router.navigate(['../passengers'], { relativeTo: this.activatedRoute });
      } else {
        // // console.log(results.value)
        this.passengers = results;
        this.flightSvc.getFullFare(data)
        .then(data => {
          this.selectedOffer = data.offers.find(offer => offer.id === off); 
          this.order = this.createOrder();
        });
      }
    });
    
    this.order = this.createOrder();
  }

  ngOnDestroy(): void {
    
  }

  processOrder() {
    let token = localStorage.getItem('token');
    const data = this.order.value;
    this.loading = true;

    this.flightSvc.postOrderCreate(data, token)
    .then(results => {
      // console.log(results['bookingReference'])
      localStorage.setItem('bookingRef', results['bookingReference']);
      this.router.navigate(['../confirmation'], { relativeTo: this.activatedRoute });
    })
    .catch(error => {
      // console.log(error)
      this.messageService.add({severity:'error', summary: error.status, detail: `${error.error.error}, please start a new search. Redirecting you in 3 seconds.`});
      setTimeout(() => {
        this.router.navigate(['/']);
      }, 3000);
    });

  }

  private createOrder(): FormGroup {
    let selectedOffer = this.fb.array([ this.selectedOffer?.id ]);

    let payment = this.fb.group({
      type: 'balance',
      currency: this.selectedOffer?.totalCurrency,
      amount: this.selectedOffer?.totalAmount
    });

    let payments = this.fb.array([ payment ]);

    return this.fb.group({
      type: 'instant',
      selectedOffers: selectedOffer,
      payments: payments,
      passengers: this.passengers
    });
  }
}
