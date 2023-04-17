import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, merge } from 'rxjs';
import { CabinClass, PassengerType } from 'src/app/models/common.models';
import { OfferRequest, Passenger, Slice } from 'src/app/models/offer-request.models';
import { FlightSearchService } from 'src/app/services/flight-search.service';
import { enumValidator } from 'src/app/utils';

@Component({
  selector: 'app-flight-search',
  templateUrl: './flight-search.component.html',
  styleUrls: ['./flight-search.component.css']
})
export class SearchflightComponent implements OnInit {

  cabinClassOptions = Object.values(CabinClass);
  request!: FormGroup;
  adultPassengers!: FormArray;
  agePassengers!: FormArray;
  passengers!: FormArray;
  offers$!: Subscription;

  constructor(
    private fb: FormBuilder, 
    private flightSvc: FlightSearchService, 
    private router: Router,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.request = this.createRequest();
  }

  processOfferRequest() {
    const { origin, destination, departureDate, adultPassengers, agePassengers } = this.request.value;
    const slice: Slice = { origin, destination, departureDate };
    const passengers = (<Passenger[]>adultPassengers).concat(<Passenger[]>agePassengers);
    
    const offerRequest: OfferRequest = {
      slices: [slice],
      passengers: passengers,
      maxConnections: 2,
      cabinClass: this.request.value['cabinClass']
    };
    this.flightSvc.postOffers({offerRequest})
      .catch(error => {
        console.log(error);
      });
    this.offers$ = this.flightSvc.onSearchResults.subscribe(
      (o) => {
        this.router.navigate([], { queryParams: { prq: o.id } });
        window.history.replaceState({}, '', '/');
      }
    )
    this.router.navigate(['/results'], { replaceUrl: true });
  }

  addAdultPassenger() {
    this.adultPassengers.push(this.createAdultPassenger());
  }

  removeAdultPassenger() {
    this.adultPassengers.removeAt(-1);
  }

  addAgePassenger() {
    this.agePassengers.push(this.createAgePasssengers());
  }

  removeAgePassenger(i: number) {
    this.agePassengers.removeAt(i);
  }

  private createRequest(): FormGroup {
    this.adultPassengers = this.fb.array([ this.createAdultPassenger() ]);
    this.agePassengers = this.fb.array([]);
    return this.fb.group({
      origin: this.fb.control<String>('', [ Validators.required ]),
      destination: this.fb.control<String>('', [ Validators.required ]),
      departureDate: this.fb.control<String>('', [ Validators.required ]),
      adultPassengers: this.adultPassengers, 
      agePassengers: this.agePassengers,
      // maxConnections: this.fb.control<Number>(0, [ Validators.required ]),
      cabinClass: this.fb.control<CabinClass>(CabinClass.economy, [ Validators.required, enumValidator(CabinClass) ])
    });
  }

  private createAdultPassenger(): FormGroup {
    return this.fb.group({
      type: this.fb.control<PassengerType>(PassengerType.adult)
    })
  }

  private createAgePasssengers(): FormGroup {
    return this.fb.group({
      age: this.fb.control<Number>(1)
    })
  }

  // private createPassenger(): FormGroup {
  //   return this.fb.group({
  //     type: this.fb.control<PassengerType>(PassengerType.adult, [ Validators.required, enumValidator(PassengerType) ]),
  //     age: this.fb.control<Number>(1, [ Validators.required ])
  //   }, { validator: this.validatePassenger });
  // }

  // private validatePassenger(fg: FormGroup): {[key: string]: any} | null {
  //   const type = fg.get('type')?.value;
  //   const age = fg.get('age')?.value;
  //   if (!type && !age) {
  //     return { 'bothMissing': true };
  //   }
  //   if (type && age) {
  //     return { 'bothPresent': true };
  //   }
  //   return null;
  // }
}
