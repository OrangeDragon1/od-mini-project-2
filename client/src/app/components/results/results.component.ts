import { AfterViewInit, Component, OnChanges, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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

  @HostListener('window:popstate', ['$event'])
  onPopState(event: any) {
    console.log('Back button pressed');
    this.selected = false;
  }

  selectedOffer?: PartialOfferOffer;

  partialOffer?: PartialOffer;
  partialOfferSlice?: PartialOfferSlice[] = [];
  partialOfferPassenger?: PartialOfferPassenger[] = [];
  partialOfferOffer?: PartialOfferOffer[] = [];
  sub$!: Subscription;
  selected = false;
  items: MenuItem[] = [];

  constructor(
    private flightSvc: FlightSearchService,
    private activatedRoute: ActivatedRoute,
    private router: Router
    ) { }

  ngOnInit(): void {
    if (this.activatedRoute.snapshot.queryParams['po']) {
      const partialOfferOfferId = this.activatedRoute.snapshot.queryParams['po'];
      this.offerSelected(partialOfferOfferId);
      console.log('testing')
    } else if (this.activatedRoute.snapshot.queryParams['prq']) {  
      const prq = this.activatedRoute.snapshot.queryParams['prq'];
      this.flightSvc.postOffers({ prq: prq });
    } 
    this.sub$ = this.flightSvc.onSearchResults.subscribe(
      partialOffer => {
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
      // {label:'Lionel Messi', url: 'https://en.wikipedia.org/wiki/Lionel_Messi'}
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
    console.log(data);
    this.router.navigate([], { queryParams: newParams });
  }

  onSelectedOffer(po: PartialOfferOffer) {
    this.selectedOffer = po;
  }

}
