import { AfterViewInit, Component, OnChanges, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { OfferRequest } from 'src/app/models/offer-request.models';
import { PartialOffer, PartialOfferOffer, PartialOfferPassenger, PartialOfferSlice } from 'src/app/models/partial-offer.models';
import { FlightSearchService } from 'src/app/services/flight-search.service';
import { HostListener } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-results',
  templateUrl: './results.component.html',
  styleUrls: ['./results.component.scss']
})
export class ResultsComponent implements OnInit {

  selectedOffer?: PartialOfferOffer;
  selectedOfferPRQ?: string;

  partialOffer?: PartialOffer;
  partialOfferSlice?: PartialOfferSlice[] = [];
  partialOfferPassenger?: PartialOfferPassenger[] = [];
  partialOfferOffer?: PartialOfferOffer[] = [];
  sub$!: Subscription;
  selected = false;
  items: MenuItem[] = [];

  private history: string[] = [];

  constructor(
    private flightSvc: FlightSearchService,
    private activatedRoute: ActivatedRoute,
    private router: Router
    ) { }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(params => {
      if (params['po']) {
        this.selected = true;
      } else {
        this.selected = false;
      }
    });

    console.log('results.component ngOnInit called!')
    if (this.activatedRoute.snapshot.queryParams['po']) {
      const partialOfferOfferId = this.activatedRoute.snapshot.queryParams['po'];
      this.offerSelected(partialOfferOfferId);
    } else if (this.activatedRoute.snapshot.queryParams['prq']) {  
      const prq = this.activatedRoute.snapshot.queryParams['prq'];
      this.flightSvc.postOffers({ prq: prq });
    } 
    this.sub$ = this.flightSvc.onSearchResults.subscribe(
      partialOffer => {
        console.log(partialOffer)
        this.partialOffer = partialOffer;
        this.partialOfferSlice = partialOffer.slices;
        this.partialOfferPassenger = partialOffer.passengers;
        this.partialOfferOffer = partialOffer.offers;
      }
    );

    this.items = [
      {label:'Home'},
      {label:'Outbound'},
      {label:'Fare options', style: {'font-weight': 'bold'}}
    ];
   
  }

  offerSelected(partialOfferOfferId: string) {
    this.selected = true;
    
    const currentParams = this.activatedRoute.snapshot.queryParams;
    const newParams = { ...currentParams, po: partialOfferOfferId};

    const data = {
      partialOfferRequestId: this.partialOffer?.id,
      selectedPartialOffer: partialOfferOfferId
    };
    this.flightSvc.getFullFare(data);
    this.router.navigate([], { queryParams: newParams });
  }

  onSelectedOffer(selectedOffer: any) {
    this.selectedOffer = selectedOffer['off'];
    this.selectedOfferPRQ = selectedOffer['prq'];
  }

}
