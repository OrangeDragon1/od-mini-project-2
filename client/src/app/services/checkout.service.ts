import { Injectable } from "@angular/core";
import { BehaviorSubject, Subject } from "rxjs";
import { PartialOfferOffer } from "../models/partial-offer.models";
import { FormArray, FormBuilder } from "@angular/forms";

@Injectable()
export class CheckoutService {

  constructor(private fb: FormBuilder) { }
  
  data = new BehaviorSubject<FormArray>(this.fb.array([]));

}