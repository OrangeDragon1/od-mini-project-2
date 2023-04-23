import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { MessageService } from 'primeng/api';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-forget-password',
  templateUrl: './forget-password.component.html',
  styleUrls: ['./forget-password.component.scss']
})
export class ForgetPasswordComponent implements OnInit {

  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private userSvc: UserService,
    private messageService: MessageService
  ) { }

  ngOnInit(): void {
    this.form = this.createForm();
  }

  processForm() {
    const data = this.form.value;
    this.userSvc.postForgotPassword(data);
    this.messageService.add({severity:'info', summary: 'Info', detail: 'An email will be sent shortly if the email is registered in our system.'});
  }

  createForm(): FormGroup {
    return this.fb.group({
      email: this.fb.control('', [ Validators.required, Validators.email ])
    })
  }
}
