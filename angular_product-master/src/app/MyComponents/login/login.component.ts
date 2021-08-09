import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UserDataService } from 'src/app/userdata.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  loginform = new FormGroup({
    Email: new FormControl('', [Validators.required, Validators.email]),
    Password: new FormControl('', [Validators.required]),
  });
  details;
  userdata: UserDataService;
  router: Router;
  forget = 0;

  constructor(userdata: UserDataService, router: Router) {
    this.userdata = userdata;
    this.router = router;
  }

  ngOnInit(): void {
    let token = JSON.parse(localStorage.getItem('token'));
    if (token != null) this.router.navigate(['mainscreen']);
  }
  OnSubmit() {
    this.forget = 0;
    if (this.loginform.valid) {
      var Email = this.loginform.value.Email;
      var pass = this.loginform.value.Password;
      this.userdata.get_user_api(Email, pass).subscribe(
        (data) => {
          this.details = data;

          if (data != null) {
            localStorage.setItem('token', JSON.stringify(data));
            this.router.navigate(['mainscreen']);
          } else {
            this.userdata.alertConfirmation(
              'error',
              'Either Username or password is incorrect'
            );
          }
        },
        (error) => {
          this.userdata.alertConfirmation(
            'error',
            'Either Username or password is incorrect'
          );
        }
      );
    }
  }
  onforget() {
    this.forget = 1;
  }
  getpass() {
    this.userdata.forget_pass(this.loginform.value.Email).subscribe(
      (data) => {
        this.userdata.alertConfirmation(
          'success',
          'New password has been sent to your registered email id'
        );

        this.ngOnInit();
      },
      (error) => {
        this.userdata.alertConfirmation('error', 'Email does not exists');
      }
    );
  }
}
