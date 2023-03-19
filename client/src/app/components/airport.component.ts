import { Component, OnInit } from '@angular/core';
import { AirportService } from '../airport.service';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Airport } from '../models';

@Component({
  selector: 'app-airport',
  templateUrl: './airport.component.html',
  styleUrls: ['./airport.component.css']
})
export class AirportsComponent implements OnInit {

  query: string = 'new york'
  airports: Airport[] = []

  constructor(private airportSvc: AirportService, private fb: FormBuilder) { }

  ngOnInit(): void {
    // console.log(this.query)
    // this.airportSvc.getAirports(this.query)
    // .then((response:any) => {
    //   this.airports = response['data']
    // })
    // .catch(
    //   error => console.log(error)
    // )
  }


}
