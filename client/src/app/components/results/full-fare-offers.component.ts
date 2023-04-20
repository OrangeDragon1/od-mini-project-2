import { AfterContentChecked, AfterViewChecked, AfterViewInit, Component, OnChanges, OnDestroy, OnInit, Output, SimpleChanges } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subject, Subscription } from 'rxjs';
import { PartialOffer, PartialOfferOffer, PartialOfferOfferSlice, PartialOfferOfferSliceSegment, PartialOfferOfferSliceSegmentPassenger, PartialOfferPassenger, PartialOfferSlice } from 'src/app/models/partial-offer.models';
import { FlightSearchService } from 'src/app/services/flight-search.service';

@Component({
  selector: 'app-full-fare-offers',
  templateUrl: './full-fare-offers.component.html',
  styleUrls: ['./full-fare-offers.component.scss']
})
export class FullFareOffersComponent implements OnInit, OnDestroy {

  @Output() onSelectedOffer = new Subject<any>();
  @Output() onSelectedOfferPRQ = new Subject<String | undefined>();
  selectedOffer: any;

  sub$!: Subscription;

  partialOffer?: PartialOffer;
  partialOfferSlice?: PartialOfferSlice[] = [];
  partialOfferPassenger?: PartialOfferPassenger[] = [];
  partialOfferOffer?: PartialOfferOffer[] = [];

  first?: PartialOfferOffer;
  firstSlice?: PartialOfferOfferSlice;
  firstSliceSegment?: PartialOfferOfferSliceSegment;
  firstSliceSegmentPassenger?: PartialOfferOfferSliceSegmentPassenger;

  dayDiff: number = 0;
  str: string = ''

  constructor(
    private flightSvc: FlightSearchService,
    private activatedRoute: ActivatedRoute
  ) { }
  
  ngOnInit(): void {
    let prq = this.activatedRoute.snapshot.queryParams['prq'];
    let po = this.activatedRoute.snapshot.queryParams['po'];
    
    const data = {
      partialOfferRequestId: prq,
      selectedPartialOffer: po
    }
    this.flightSvc.getFullFare(data);
    this.sub$ = this.flightSvc.onFullFareOfferResults.subscribe(
      partialOffer => {
        this.partialOffer = partialOffer;
        this.partialOfferSlice = partialOffer.slices;
        this.partialOfferPassenger = partialOffer.passengers;
        this.partialOfferOffer = partialOffer.offers.filter(v => 
          v.slices.at(0)?.segments.at(0)?.passengers.at(0)?.cabinClass == this.partialOffer?.cabinClass
        );
        
        this.first = partialOffer.offers.at(0);
        this.firstSlice = partialOffer.offers.at(0)?.slices.at(0);
        this.firstSliceSegment = partialOffer.offers.at(0)?.slices.at(0)?.segments.at(0);
        this.firstSliceSegmentPassenger = partialOffer.offers.at(0)?.slices.at(0)?.segments.at(0)?.passengers.at(0);
        this.dayDiff = this.sameDay(this.firstSliceSegment?.departingAt, this.firstSliceSegment?.arrivingAt);
      }
    )
    this.str.toLowerCase
  }
  
  ngOnDestroy(): void {
    
  }
  
  onRadioClick() {
    const selectedOffer = {
      prq: this.partialOffer?.id,
      off: this.selectedOffer
    }
    this.onSelectedOffer.next(selectedOffer);
  }

  private sameDay(departure: any, arrival: any): number {
    const departingDate = departure.split("T")[0];
    const arrivingDate = arrival.split("T")[0];
    const dayDiff = (Date.parse(arrivingDate) - Date.parse(departingDate)) 
        / (24 * 60 * 60 * 1000);
    return dayDiff;
  }

}
