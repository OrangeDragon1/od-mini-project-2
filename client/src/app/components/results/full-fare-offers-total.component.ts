import { AfterContentChecked, Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PartialOfferOffer } from 'src/app/models/partial-offer.models';

@Component({
  selector: 'app-full-fare-offers-total',
  templateUrl: './full-fare-offers-total.component.html',
  styleUrls: ['./full-fare-offers-total.component.scss']
})
export class FullFareOffersTotalComponent implements OnInit, AfterContentChecked {

  @Input() selectedOffer?: PartialOfferOffer; 
  checkout = false;

  constructor(private router: Router) {

  }

  ngOnInit(): void {
    this.selectedOffer = undefined;
  }

  ngAfterContentChecked(): void {
    if (this.selectedOffer) {
      this.checkout = true;
    }
  }

  onCheckout() {
    console.log('onCheckout called!')
    this.router.navigate(['/checkout', 'prq' ,this.selectedOffer?.id])
  }
}
