import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'  

import { AppComponent } from './app.component';
import { MenubarComponent } from './components/menu-bar.component';
import { PrimeNGModule } from './primeng.module';
import { SearchflightComponent } from './components/home/flight-search.component';
import { HomeComponent } from './components/home/home.component';
import { FlightSearchService } from './services/flight-search.service';
import { HttpClientModule } from '@angular/common/http';
import { ResultsComponent } from './components/results/results.component';
import { OffersComponent } from './components/results/offers.component';
import { FullFareOffersComponent } from './components/results/full-fare-offers.component';
import { DurationPipe } from './duration.pipe';
import { FullFareOffersTotalComponent } from './components/results/full-fare-offers-total.component';
import { CheckoutComponent } from './components/checkout/checkout.component';
import { OffersSidePanelComponent } from './components/results/offers-side-panel.component';

const appRoutes: Routes = [
  { path: '', component: HomeComponent },
  // { path: 'results/:offerRequest', component: ResultsComponent },
  { path: 'results', component: ResultsComponent },
  { path: 'checkout/:prq/:off', component: CheckoutComponent },
  { path: '**', redirectTo: '/', pathMatch: 'full' }
];

@NgModule({
  declarations: [
    AppComponent,
    MenubarComponent,
    SearchflightComponent,
    HomeComponent,
    ResultsComponent,
    OffersComponent,
    FullFareOffersComponent,
    DurationPipe,
    FullFareOffersTotalComponent,
    CheckoutComponent,
    OffersSidePanelComponent
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
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
