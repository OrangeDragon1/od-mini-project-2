import { trigger, state, style, transition, animate } from '@angular/animations';
import { Component, Input, OnDestroy, OnInit, Output, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject, Subscription } from 'rxjs';
import { PartialOfferOffer, PartialOfferOfferSliceSegment } from 'src/app/models/partial-offer.models';
import { FlightSearchService } from 'src/app/services/flight-search.service';

@Component({
  selector: 'app-offers',
  templateUrl: './offers.component.html',
  styleUrls: ['./offers.component.scss']
})
export class OffersComponent implements OnInit, OnDestroy {

  partialOfferOffer?: PartialOfferOffer[] = [];
  @Output() offerSelected = new Subject<string>();
  
  sub$!: Subscription;

  constructor(
    private router: Router,
    private flightSvc: FlightSearchService,
    private activatedRoute: ActivatedRoute
    ) { }

  ngOnInit(): void {
    let prq = this.activatedRoute.snapshot.queryParams['prq'];
    if (prq) {
      this.flightSvc.postOffers({ prq: prq })
      .then(results => {
        this.partialOfferOffer = results.offers;
      });
    } else {
      this.flightSvc.onSearchResults.subscribe(
        results => {
          this.partialOfferOffer = results.offers;
        }
      )
    }
    
  }

  ngOnDestroy(): void {
    
  }

  onSelect(partialOfferId: string) {
    this.offerSelected.next(partialOfferId);
  }

}
