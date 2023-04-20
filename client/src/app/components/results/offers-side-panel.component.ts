import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PartialOffer } from 'src/app/models/partial-offer.models';
import { FlightSearchService } from 'src/app/services/flight-search.service';

@Component({
  selector: 'app-offers-side-panel',
  templateUrl: './offers-side-panel.component.html',
  styleUrls: ['./offers-side-panel.component.scss']
})
export class OffersSidePanelComponent implements OnInit {

  partialOffer?: PartialOffer;

  constructor(
    private activatedRoute: ActivatedRoute,
    private flightSvc: FlightSearchService
  ) { }

  ngOnInit(): void {
    let prq = this.activatedRoute.snapshot.queryParams['prq'];
    if (prq) {
      this.flightSvc.postOffers({ prq: prq })
      .then(results => {
        this.partialOffer = results;
      })
    } else {
      this.flightSvc.onSearchResults.subscribe(results => {
        this.partialOffer = results;
      })
    }
  }

  onEditSearch() {
    
  }
}
