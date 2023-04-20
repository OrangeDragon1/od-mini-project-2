import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { PartialOfferOffer } from 'src/app/models/partial-offer.models';
import { CheckoutService } from 'src/app/services/checkout.service';
import { FlightSearchService } from 'src/app/services/flight-search.service';

@Component({
  selector: 'app-checkout-payment',
  templateUrl: './checkout-payment.component.html',
  styleUrls: ['./checkout-payment.component.scss']
})
export class CheckoutPaymentComponent implements OnInit {

  sub$!: Subscription

  passengers?: FormArray;
  selectedOffer?: PartialOfferOffer;
  order!: FormGroup;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private flightSvc: FlightSearchService,
    private fb: FormBuilder,
    private checkoutSvc: CheckoutService
  ) { }

  ngOnInit(): void {
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
        console.log(results.value)
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

  processOrder() {
    const data = this.order.value;
    this.flightSvc.postOrderCreate(data);
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
