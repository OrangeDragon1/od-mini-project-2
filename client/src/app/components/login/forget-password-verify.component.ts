import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-forget-password-verify',
  templateUrl: './forget-password-verify.component.html',
  styleUrls: ['./forget-password-verify.component.scss']
})
export class ForgetPasswordVerifyComponent implements OnInit {

  validString?: boolean;
  error?: any;
  result?: any;
  errorMessage = '';

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private userSvc: UserService
  ) { }

  ngOnInit() {
    let resetPwdString = this.activatedRoute.snapshot.queryParams['resetPwdString'];
    this.userSvc.putResetPassword(resetPwdString)
    .then(results => {
      this.result = results;
      if (results.success) {
        this.validString = true;
      }
    })
    .catch(error => {
      this.error = error;
      this.errorMessage = error.error.error;
    })
    // this.userSvc.getVerified(verificationString)
    // .then(results => {
    //   if (results.success) {
    //     this.validString = true;
    //   }
    // })
    // .catch(error => {
    //   this.errorMessage = error.error.error;  
    // })
  }

}
