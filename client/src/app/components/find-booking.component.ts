import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { FlightSearchService } from '../services/flight-search.service';

@Component({
  selector: 'app-find-booking',
  templateUrl: './find-booking.component.html',
  styleUrls: ['./find-booking.component.scss']
})
export class FindBookingComponent implements OnInit, OnDestroy {

  form!: FormGroup;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private messageService: MessageService,
    private flightSvc: FlightSearchService
  ) { }

  ngOnInit(): void {
    this.form = this.createForm();
  }

  ngOnDestroy(): void {
    this.loading = false;
  }

  processForm() {
    this.loading = true;
    let data:string = this.form.value['bookingRef'].toUpperCase();
    this.flightSvc.getOrderByBookingRef(data)
    .then(results => {
      this.loading = false;
      this.router.navigate(['/booking', data]);
    })
    .catch(err => {
      this.loading = false;
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Unable to find booking' });
    })
  }

  createForm(): FormGroup {
    return this.fb.group({
      bookingRef: this.fb.control('', [ Validators.required, Validators.minLength(4) ])
    })
  }

}
