import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserDataService } from 'src/app/userdata.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
  router;
  token;
  user;
  userdata: UserDataService;
  changePassword: FormGroup;
  constructor(
    router: Router,
    userdata: UserDataService,
    private formBuilder: FormBuilder
  ) {
    this.router = router;
    this.userdata = userdata;
    this.changePassword = this.formBuilder.group(
      {
        password: new FormControl(null, [Validators.required]),
        confirmPassword: new FormControl('', [Validators.required]),
      },
      {
        validator: this.MustMatch('password', 'confirmPassword'),
      }
    );
  }
  submitted: boolean = false;

  MustMatch(controlName: string, matchingControlName: string) {
    return (formGroup: FormGroup) => {
      const control = formGroup.controls[controlName];
      const matchingControl = formGroup.controls[matchingControlName];
      if (matchingControl.errors && !matchingControl.errors.mustMatch) {
        return;
      }
      if (control.value !== matchingControl.value) {
        matchingControl.setErrors({ mustMatch: true });
      } else {
        matchingControl.setErrors(null);
      }
    };
  }
  id;
  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem('currentUser'));
  }
  setClickedProfile() {
    let cl = JSON.parse(localStorage.getItem('currentUser'));
    let clickedUser = { id: cl.id, profilePicture: cl.profilePicture };
    localStorage.setItem('clickedUser', JSON.stringify(clickedUser));
    if (this.router.url === '/mainscreen/profile/view')
      this.reloadCurrentRoute();
  }

  click(): void {}
  SignOut() {
    localStorage.clear();
  }
  reloadCurrentRoute() {
    const currentUrl = this.router.url;
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.router.navigate([currentUrl]);
    });
  }

  onSubmit() {
    this.submitted = true;
    if (this.changePassword.invalid) {
      return;
    }
    this.userdata.update_password(this.user.id, this.changePassword.value);
  }
}
