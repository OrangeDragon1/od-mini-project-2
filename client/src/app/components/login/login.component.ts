import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  form!: FormGroup;
  buttonDisabled = false;

  constructor(
    private fb: FormBuilder,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private userSvc: UserService,
    private messageService: MessageService
  ) { }

  ngOnInit(): void {
    this.form = this.createForm();
  }

  login() {
    console.log(this.form.value);
    const data = this.form.value;
    this.userSvc.postLogin(data)
    .then(results => {
      if(results?.success) {
        console.log(results.token)
        this.messageService.add({severity:'success', summary: 'Success', detail: 'Welcome back! You will be redirected in 3 seconds.'});
        this.buttonDisabled = true;
        localStorage.setItem('token', results.token);
        setTimeout(() => {
          this.router.navigate(['/']);
        }, 3000);
        setTimeout(() => {
          location.reload();
        }, 3000);
      }
    })
    .catch(error => {
      let errorMessage = error.error.error as string;
      if (errorMessage.includes('User')) {
        this.messageService.add({severity:'error', summary: 'Error', detail: errorMessage});
      } else {
        this.messageService.add({severity:'error', summary: 'Error', detail: 'Incorrect email or password'});
      }
    })
  }

  createForm(): FormGroup {
    return this.fb.group({
      email: this.fb.control('', [ Validators.required, Validators.email ]),
      password: this.fb.control('', [ Validators.required ])
    })
  }
}
