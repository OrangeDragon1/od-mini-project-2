import { AfterContentChecked, AfterViewChecked, AfterViewInit, Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PartialOfferOffer } from 'src/app/models/partial-offer.models';
import { FlightSearchService } from 'src/app/services/flight-search.service';

@Component({
  selector: 'app-full-fare-offers-total',
  templateUrl: './full-fare-offers-total.component.html',
  styleUrls: ['./full-fare-offers-total.component.scss']
})
export class FullFareOffersTotalComponent implements OnInit, OnChanges {

  @Input() selectedOffer?: PartialOfferOffer;
  @Input() selectedOfferPRQ?: string;
  checkout = false;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private flightSvc: FlightSearchService
  ) { }

  ngOnInit(): void {
    this.selectedOffer = undefined;
    this.selectedOfferPRQ = undefined;
  }

  ngOnChanges(changes: SimpleChanges): void {
    if ('selectedOffer' in changes) {
      if (this.selectedOffer) {
        this.checkout = true;
      }
    }
  }

  onCheckout() {
    console.log('onCheckout called!')
    this.flightSvc.selectedOffer.next(this.selectedOffer);
    let po = this.activatedRoute.snapshot.queryParams['po']
    this.router.navigate(['/checkout', this.selectedOfferPRQ, po ,this.selectedOffer?.id]);
  }
}
