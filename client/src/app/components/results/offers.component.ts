import { trigger, state, style, transition, animate } from '@angular/animations';
import { Component, Input, OnDestroy, OnInit, Output, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { Subject, Subscription } from 'rxjs';
import { PartialOfferOffer, PartialOfferOfferSliceSegment } from 'src/app/models/partial-offer.models';
import { FlightSearchService } from 'src/app/services/flight-search.service';

@Component({
  selector: 'app-offers',
  templateUrl: './offers.component.html',
  styleUrls: ['./offers.component.scss']
})
export class OffersComponent implements OnInit, OnDestroy {

  @Input() partialOfferOffer: PartialOfferOffer[] = [];
  @Output() offerSelected = new Subject<string>();
  
  sub$!: Subscription;

  constructor(
    private router: Router,
    private flightSvc: FlightSearchService
    ) { }

  ngOnInit(): void {
    
  }

  ngOnDestroy(): void {

  }

  onSelect(partialOfferId: string) {
    this.offerSelected.next(partialOfferId);
  }

  private sameDay(departure: any, arrival: any): number {
    console.log(departure);
    console.log(arrival);

    const departingDate = departure.split("T")[0];
    const arrivingDate = arrival.split("T")[0];
    const dayDiff = (Date.parse(arrivingDate) - Date.parse(departingDate)) 
        / (24 * 60 * 60 * 1000);
    console.log(dayDiff)

    return dayDiff;
  }
}
