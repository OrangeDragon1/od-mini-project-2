import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { User } from 'src/app/models/common.models';
import { UserService } from 'src/app/services/user.service';
import { passwordMatchValidator } from 'src/app/utils';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {

  user?: User;
  form!: FormGroup;

  constructor(
    private userSvc: UserService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private messageService: MessageService
  ) { }

  ngOnInit(): void {
    let token = localStorage.getItem('token');
    if (token) {
        this.userSvc.getUser(token)
        .then(results => {
          this.user = results;
        })
        .catch(error => {
          if (error.status == 403) {
            localStorage.removeItem('token');
            this.router.navigate(['/']);
            setTimeout(() => {
              location.reload();
            }, 100);
          }
        })
    } else {
      this.router.navigate(['/']);
        setTimeout(() => {
          location.reload();
        }, 100);
    }
    this.form = this.createForm();
  }

  processForm() {
    let token = localStorage.getItem('token');
    const { oldpassword, password, repassword } = this.form.value;
    const data = {
      password: oldpassword,
      newPassword: password
    }
    // console.log(data);
    this.userSvc.putChangePassword(data, token)
    .then(results => {
      this.messageService.add({severity:'success', summary: 'Success', detail: results.success});
    })
    .catch(errors => {
      this.messageService.add({severity:'error', summary: 'Error', detail: errors.error.error});
    }) 
    
  }

  createForm(): FormGroup {
    return this.fb.group({
      oldpassword: this.fb.control('', [ Validators.required ]),
      password: this.fb.control('', [ Validators.required, Validators.minLength(8) ] ),
      repassword: this.fb.control('', [ Validators.required, Validators.minLength(8) ])
    }, { validators: passwordMatchValidator })
  }
}
