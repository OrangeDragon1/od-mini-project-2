import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom, Subject } from "rxjs";
import { OfferRequest } from "../models/offer-request.models";
import { PartialOffer } from "../models/partial-offer.models";

@Injectable()
export class FlightSearchService {

  onSearchResults = new Subject<PartialOffer>();
  onFullFareOfferResults = new Subject<PartialOffer>();

  constructor(private http: HttpClient) { }

  postOffers(options: {offerRequest?: OfferRequest, prq?: String}): Promise<PartialOffer>{
    const headers = new HttpHeaders()
        .set('Content-Type','application/json')
        .set('Accept','application/json');
    let data: any;
    if (options.offerRequest != null) {
      data = {
        data: options.offerRequest ? options.offerRequest : null
      }
    } else {
      data = { 
        prq: options.prq ? options.prq : null  
      }
    }
    return firstValueFrom<PartialOffer>(
      this.http.post<PartialOffer>('/api/v1/search/offers', data, { headers })
    )
    .then(results => {
      this.onSearchResults.next(results);
      return results;
    });
  } 

  getFullFare(data: any): Promise<PartialOffer> {
    let params = new HttpParams()
        .set('partialOfferRequestId', data.partialOfferRequestId)
        .set('selectedPartialOffer', data.selectedPartialOffer)
    return firstValueFrom<PartialOffer>(
      this.http.get<PartialOffer>('/api/v1/search/fullFare', { params })
    )
    .then(results => {
      this.onFullFareOfferResults.next(results);
      return results;
    });
  }

}


