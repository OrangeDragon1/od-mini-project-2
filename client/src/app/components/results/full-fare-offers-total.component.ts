import { AfterContentChecked, AfterViewChecked, AfterViewInit, Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { User } from 'src/app/models/common.models';
import { PartialOffer, PartialOfferOffer } from 'src/app/models/partial-offer.models';
import { FlightSearchService } from 'src/app/services/flight-search.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-full-fare-offers-total',
  templateUrl: './full-fare-offers-total.component.html',
  styleUrls: ['./full-fare-offers-total.component.scss']
})
export class FullFareOffersTotalComponent implements OnInit, OnChanges {

  @Input() selectedOffer?: PartialOfferOffer;
  @Input() selectedOfferPRQ?: string;

  partialOffer?: PartialOffer;
  checkout = false;
  user?: User;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private flightSvc: FlightSearchService,
    private userSvc: UserService
  ) { }

  ngOnInit(): void {
    let token = localStorage.getItem('token');
    if (token) {
      this.userSvc.getUser(token)
      .then(results => {
        this.user = results;
      })
    }
    
    this.selectedOffer = undefined;
    this.selectedOfferPRQ = undefined;

    let prq = this.activatedRoute.snapshot.queryParams['prq'];
    let po = this.activatedRoute.snapshot.queryParams['po'];

    const data = {
      partialOfferRequestId: prq,
      selectedPartialOffer: po
    }

    this.flightSvc.getFullFare(data)
    .then(results => {
        this.partialOffer = results;
      }
    )
  }

  ngOnChanges(changes: SimpleChanges): void {
    if ('selectedOffer' in changes) {
      if (this.selectedOffer) {
        this.checkout = true;
      }
    }
  }

  onCheckout() {
    // console.log('onCheckout called!')
    this.flightSvc.selectedOffer.next(this.selectedOffer);
    let po = this.activatedRoute.snapshot.queryParams['po']
    this.router.navigate(['/checkout', this.selectedOfferPRQ, po ,this.selectedOffer?.id, 'passengers']);
  }
}
