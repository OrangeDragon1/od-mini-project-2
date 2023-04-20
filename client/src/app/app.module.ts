import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'  

import { AppComponent } from './app.component';
import { MenubarComponent } from './components/menu-bar.component';
import { PrimeNGModule } from './primeng.module';
import { FlightSearchComponent } from './components/home/flight-search.component';
import { HomeComponent } from './components/home/home.component';
import { FlightSearchService } from './services/flight-search.service';
import { HttpClientModule } from '@angular/common/http';
import { ResultsComponent } from './components/results/results.component';
import { OffersComponent } from './components/results/offers.component';
import { FullFareOffersComponent } from './components/results/full-fare-offers.component';
import { DurationPipe } from './utils.pipe';
import { FullFareOffersTotalComponent } from './components/results/full-fare-offers-total.component';
import { CheckoutPassengersComponent } from './components/checkout/checkout-passengers.component';
import { OffersSidePanelComponent } from './components/results/offers-side-panel.component';
import { CheckoutComponent } from './components/checkout/checkout.component';
import { CheckoutPaymentComponent } from './components/checkout/checkout-payment.component';
import { CheckoutSuccessComponent } from './components/checkout/checkout-success.component';
import { MytripsUpcomingComponent } from './components/mytrips/mytrips-upcoming.component';
import { MytripsUpcomingEticketComponent } from './components/mytrips/mytrips-upcoming-eticket.component';
import { CheckoutService } from './services/checkout.service';

const appRoutes: Routes = [
  { path: '', component: HomeComponent },
  // { path: 'results/:offerRequest', component: ResultsComponent },
  { path: 'results', component: ResultsComponent },
  { path: 'checkout/:prq/:po/:off', component: CheckoutComponent, children: [
    { path: 'passengers', component: CheckoutPassengersComponent },
    { path: 'payment', component: CheckoutPaymentComponent },
    { path: 'confirmation', component: CheckoutSuccessComponent }
  ] },
  { path: '**', redirectTo: '/', pathMatch: 'full' }
];

@NgModule({
  declarations: [
    AppComponent,
    MenubarComponent,
    FlightSearchComponent,
    HomeComponent,
    ResultsComponent,
    OffersComponent,
    FullFareOffersComponent,
    DurationPipe,
    FullFareOffersTotalComponent,
    CheckoutPassengersComponent,
    OffersSidePanelComponent,
    CheckoutComponent,
    CheckoutPaymentComponent,
    CheckoutSuccessComponent,
    MytripsUpcomingComponent,
    MytripsUpcomingEticketComponent
  ],
  imports: [
    BrowserModule,
    PrimeNGModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes, { useHash: true }),
    BrowserAnimationsModule
  ],
  providers: [
    FlightSearchService,
    CheckoutService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
