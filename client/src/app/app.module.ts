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
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
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
import { CheckoutService } from './services/checkout.service';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/login/register.component';
import { UserService } from './services/user.service';
import { MessageService } from 'primeng/api';
import { ForgetPasswordComponent } from './components/login/forget-password.component';
import { RegisterVerifyComponent } from './components/login/register-verify.component';
import { ForgetPasswordVerifyComponent } from './components/login/forget-password-verify.component';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { BookingService } from './services/booking.service';
import { ChangePasswordComponent } from './components/user/change-password.component';
import { MyTripsComponent } from './components/user/my-trips.component';
import { BookingComponent } from './components/booking.component';
import { StyleDirective } from './style.directive';
import { FindBookingComponent } from './components/find-booking.component';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { ConfirmationService } from 'primeng/api';
import { CorsInterceptor } from './cors.interceptor';

const appRoutes: Routes = [
  { path: '', component: HomeComponent },
  // { path: 'results/:offerRequest', component: ResultsComponent },
  { path: 'results', component: ResultsComponent },
  { path: 'checkout/:prq/:po/:off', component: CheckoutComponent, children: [
    { path: 'passengers', component: CheckoutPassengersComponent },
    { path: 'payment', component: CheckoutPaymentComponent },
    { path: 'confirmation', component: CheckoutSuccessComponent }
  ] },
  { path: 'register', component: RegisterComponent},
  { path: 'verify', component: RegisterVerifyComponent},
  { path: 'forget-password' , component: ForgetPasswordComponent },
  { path: 'reset-password', component: ForgetPasswordVerifyComponent },
  { path: 'login', component: LoginComponent },
  { path: 'change-password', component: ChangePasswordComponent },
  { path: 'my-trips', component: MyTripsComponent },
  { path: 'booking/:ref', component: BookingComponent },
  { path: 'find-booking', component: FindBookingComponent },
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
    LoginComponent,
    RegisterComponent,
    ForgetPasswordComponent,
    RegisterVerifyComponent,
    ForgetPasswordVerifyComponent,
    ChangePasswordComponent,
    MyTripsComponent,
    BookingComponent,
    StyleDirective,
    FindBookingComponent
  ],
  imports: [
    BrowserModule,
    PrimeNGModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes, { useHash: true }),
    BrowserAnimationsModule,
    AutoCompleteModule,
    ConfirmPopupModule
  ],
  providers: [
    FlightSearchService,
    CheckoutService,
    UserService,
    MessageService,
    BookingService,
    ConfirmationService,
    { provide: HTTP_INTERCEPTORS, useClass: CorsInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
