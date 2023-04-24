import { Injectable } from "@angular/core";
import { Subject } from "rxjs";

@Injectable()
export class BookingService {
  
  bookingRef = new Subject<any>();

}