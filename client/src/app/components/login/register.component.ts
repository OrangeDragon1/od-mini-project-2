import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { UserService } from 'src/app/services/user.service';
import { passwordMatchValidator } from 'src/app/utils';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  registerForm!: FormGroup;
  registerResponse?: any;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private userSvc: UserService,
    private messageService: MessageService
  ) { }

  ngOnInit(): void { 
    this.registerForm = this.createRegisterForm();
  }

  register() {
    const data = this.registerForm.value;
    // console.log(data);
    this.loading = true;
    this.userSvc.postRegister(data)
    .then(results => {
      this.registerResponse = results;
      if(this.registerResponse?.error) {
        this.messageService.add({severity:'error', summary: 'Error', detail: this.registerResponse?.error});
      } else if(this.registerResponse?.success) {
        this.messageService.add({severity:'success', summary: 'Success', detail: this.registerResponse?.success});
      }
      // this.messageService.add({severity:'success', summary: 'Success', detail: 'Message Content'});
      this.loading = false;
    })
  }

  createRegisterForm(): FormGroup {
    return this.fb.group({
      email: this.fb.control('', [ Validators.required, Validators.email ]),
      firstName: this.fb.control('', [ Validators.required ]),
      lastName: this.fb.control('', [ Validators.required ]),
      password: this.fb.control('', [ Validators.required, Validators.minLength(8) ]),
      repassword: this.fb.control('', [ Validators.required, Validators.minLength(8) ])
    }, { validators: passwordMatchValidator })
  }
}
