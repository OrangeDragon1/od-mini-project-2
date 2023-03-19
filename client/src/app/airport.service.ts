import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http'; 
import { Airport } from './models';
import { firstValueFrom } from 'rxjs';

@Injectable()
export class AirportService {

  constructor(private httpClient: HttpClient) { }

  public getAirports(query: string): Promise<any>{
    const params = new HttpParams().set('query', query)
    return firstValueFrom(this.httpClient.get<any>('/api/v1/search/airports'))
  }
}
