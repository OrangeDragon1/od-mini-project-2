import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss']
})
export class CheckoutComponent implements OnInit {

  breadcrumbItems: MenuItem[] = [];
  stepsItems: MenuItem[] = [];

  ngOnInit(): void {
    this.breadcrumbItems = [
      {label:'Home'},
      {label:'Flights selection'},
      {label:'Checkout', style: {'font-weight': 'bold'}}
      // {label:'Lionel Messi', url: 'https://en.wikipedia.org/wiki/Lionel_Messi'}
    ];

    this.stepsItems = [
      {label:'Flight Summary & Passenger Info', routerLink:'passengers'},
      {label:'Payment', routerLink:'payment'},
      {label:'Confirmation', routerLink:'confirmation'}
    ];

  }
}
