import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { UserService } from '../services/user.service';
import { User } from '../models/common.models';

@Component({
  selector: 'app-menu-bar',
  templateUrl: './menu-bar.component.html',
  styleUrls: ['./menu-bar.component.scss']
})

export class MenubarComponent {

  items: MenuItem[] = [];
  user?: User;

  constructor(
    private router: Router,
    private userSvc: UserService,
    private activatedRoute: ActivatedRoute,
  ) { }

  ngOnInit() {
    let token = localStorage.getItem('token');
    if (token) {
        this.userSvc.getUser(token)
        .then(results => {
            this.user = results;
            this.items = [
                {label: 'Home', routerLink: '/'},
                {label: 'Change password', routerLink: '/change-password'},
                {label: 'My trips', routerLink: '/my-trips'},
                {label: 'Logout', command: () => this.logout()}
            ]
        })
        .catch(error => {
          if (error.status == 403) {
            localStorage.removeItem('token');
            this.router.navigate(['/']);
            setTimeout(() => {
              location.reload();
            }, 1000);
          }
        })
    } else {
        this.items = [
            {label: 'Login', routerLink: '/login'},
            {label: 'Register', routerLink: '/register' },
            {label: 'Forgot password', routerLink: '/forget-password'}
        ];
    }
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/']);
    this.user = undefined;
    setTimeout(() => {
        location.reload();
      }, 1000);
  }
}
