import { Component, Input, OnInit } from '@angular/core';
import { PartialOffer } from 'src/app/models/partial-offer.models';

@Component({
  selector: 'app-offers-side-panel',
  templateUrl: './offers-side-panel.component.html',
  styleUrls: ['./offers-side-panel.component.scss']
})
export class OffersSidePanelComponent implements OnInit {

  @Input() partialOffer?: PartialOffer;

  ngOnInit(): void {
      
  }

  onEditSearch() {
    
  }
}
