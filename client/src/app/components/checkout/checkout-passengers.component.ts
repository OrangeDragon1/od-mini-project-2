import { Component, OnDestroy, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { FlightSearchService } from 'src/app/services/flight-search.service';
import { PartialOffer, PartialOfferOffer, PartialOfferPassenger } from 'src/app/models/partial-offer.models'
import { MenuItem } from 'primeng/api';
import { nameLengthValidator } from 'src/app/utils';
import { CheckoutService } from 'src/app/services/checkout.service';

@Component({
  selector: 'app-checkout-passengers',
  templateUrl: './checkout-passengers.component.html',
  styleUrls: ['./checkout-passengers.component.scss']
})
export class CheckoutPassengersComponent implements OnInit, OnDestroy{

  sub$!: Subscription;
  selectedOffer?: PartialOfferOffer;
  
  breadcrumbItems: MenuItem[] = [];
  stepsItems: MenuItem[] = [];
  passengers!: FormArray;
  order!: FormGroup;
  identityDocuments!: FormArray;
  titlePlaceholder: string[] = [];
  genderPlaceholder: string[] = [];
  
  titles: any[] = [];
  genders: any[] = [];
  maxDate = new Date();
  maxAdultDate = new Date(new Date().setFullYear(new Date().getFullYear() - 18));
  minDate = new Date(new Date().setFullYear(new Date().getFullYear() - 130));

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private flightSvc: FlightSearchService,
    private fb: FormBuilder,
    private checkoutSvc: CheckoutService
  ) {
    this.titles = [
      {t:'Mr.', value:'mr'}, 
      {t:'Ms.', value:'ms'},
      {t:'Mrs.', value:'mrs'},
      {t:'Miss.', value:'miss'}
    ];
    this.genders = [
      {g: 'Male', value:'m'},
      {g: 'Female', value:'f'}
    ];
  }

  ngOnInit(): void {
    let prq = this.activatedRoute.parent?.snapshot.params['prq']
    let po = this.activatedRoute.parent?.snapshot.params['po']
    let off = this.activatedRoute.parent?.snapshot.params['off']
    const data = {
      partialOfferRequestId: prq,
      selectedPartialOffer: po
    }
    this.flightSvc.getFullFare(data)
    .then(results => {
      this.selectedOffer = results.offers.find(offer => offer.id === off);
      this.order = this.createOrder(results, off);        
    })
    this.order = this.createOrder();
  }

  ngOnDestroy(): void {
    
  }

  processOrder() {
    this.checkoutSvc.data.next(this.passengers);
    this.router.navigate(['../payment'], { relativeTo: this.activatedRoute });
  }

  createOrder(results?: PartialOffer, off?: any): FormGroup {
    if (!results) {
      return this.fb.group([])
    }
    let numPassengers = results?.passengers.length ?? 0;
    this.titlePlaceholder = this.createArray(numPassengers);
    this.genderPlaceholder = this.createArray(numPassengers);
    let selectedOffer = results?.offers.find(offer => offer.id === off) as PartialOfferOffer;
    this.passengers = this.fb.array([]);
    for(let i = 0; i < numPassengers; i++) {
      this.passengers.push(this.createPassenger(selectedOffer, i));
    }
    return this.fb.group({
      passengers: this.passengers
    })
  }

  createPassenger(selectedOffer: PartialOfferOffer, idx: number): FormGroup {
    if (selectedOffer.passengerIdentityDocumentsRequired && selectedOffer.allowedPassengerIdentityDocumentTypes.length > 0) {
      this.identityDocuments = this.fb.array([]);
      for(let i = 0; i < selectedOffer.allowedPassengerIdentityDocumentTypes.length; i++) {
        this.identityDocuments.push(
          this.createIdentityDocument(selectedOffer.allowedPassengerIdentityDocumentTypes[i])
          );
      }
    } 
    let type = selectedOffer.passengers[idx].type;
    let id = selectedOffer.passengers[idx].id;
    return this.fb.group({
      type: type,
      title: this.fb.control('', [ Validators.required ]),
      phoneNumber: this.fb.control('', [ Validators.required, Validators.minLength(14) ]),
      identityDocuments: this.identityDocuments,
      id: id,
      givenName: this.fb.control('', [ Validators.required, Validators.minLength(2), Validators.pattern(/^[A-Za-z\- 'À-ÖØ-öø-ÿ]+$/) ]),
      gender: this.fb.control('', [ Validators.required ]),
      familyName: this.fb.control('', [ Validators.required, Validators.minLength(2), Validators.pattern(/^[A-Za-z\- 'À-ÖØ-öø-ÿ]+$/) ]),
      email: this.fb.control('', [ Validators.required, Validators.minLength(5), Validators.maxLength(256), Validators.email ]),
      bornOn: this.fb.control('', [ Validators.required ]),
    }, { validators: nameLengthValidator });
  }

  createIdentityDocument(type: string): FormGroup {
    return this.fb.group({
      uniqueIdentifier: this.fb.control('', [ Validators.required, Validators.pattern(/^[a-zA-Z0-9]+$/) ]),
      type: type,
      issuingCountryCode: this.fb.control('', [ Validators.required, Validators.minLength(2), Validators.maxLength(2), Validators.pattern(/^[a-zA-Z]+$/) ]),
      expiresOn: this.fb.control('', [ Validators.required ])
    })
  }

  createArray(n: number) {
    const arrayOfArrays = [];
    let a = 'a';
    for (let i = 0; i < n; i++) {
      arrayOfArrays.push(a);
    }
    return arrayOfArrays;
  }
}
