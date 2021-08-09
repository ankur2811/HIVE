import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserDataService } from 'src/app/userdata.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss'],
})
export class SignupComponent implements OnInit {
  registerForm = new FormGroup({
    name: new FormControl(null, [Validators.required]),
    username: new FormControl(null, [Validators.required, Validators.email]),
    password: new FormControl('',[Validators.required]),
    gender: new FormControl ('')
  });
  http: HttpClient;
  router: Router;
  userservice: UserDataService;

  constructor(http: HttpClient, router: Router, userservice: UserDataService) {
    this.http = http;
    this.router = router;
    this.userservice = userservice;
  }

  ngOnInit(): void {}
  onSubmit() {
    if (this.registerForm.valid) {
      this.userservice.register_user(this.registerForm.value);
    }
  }
}
