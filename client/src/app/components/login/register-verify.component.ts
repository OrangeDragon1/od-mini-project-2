import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-register-verify',
  templateUrl: './register-verify.component.html',
  styleUrls: ['./register-verify.component.css']
})
export class RegisterVerifyComponent implements OnInit {

  validString = false; 
  errorMessage = '';

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private userSvc: UserService
  ) { }

  ngOnInit() {
    let verificationString = this.activatedRoute.snapshot.queryParams['verificationString'];
    this.userSvc.getVerified(verificationString)
    .then(results => {
      if (results.success) {
        this.validString = true;
      }
    })
    .catch(error => {
      this.errorMessage = error.error.error;  
    })
  }

}
