import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserActivityService } from 'src/app/user-activity.service';
import { UserDataService } from 'src/app/userdata.service';

@Component({
  selector: 'app-mainscreen',
  templateUrl: './mainscreen.component.html',
  styleUrls: ['./mainscreen.component.scss'],
})
export class MainscreenComponent implements OnInit {
  token;
  user;
  router: Router;
  constructor(
    router: Router,
    private userdata: UserDataService,
    private activity: UserActivityService
  ) {
    this.router = router;
  }

  ngOnInit(): void {
    this.token = JSON.parse(localStorage.getItem('token'));
    this.activity.set_colour();

    if (this.token === null) {
      this.router.navigate(['']);
    } else {
      this.userdata.get_user_byid(this.token.token).subscribe((data) => {
        this.user = data;
        this.userdata.set_user(this.user);
        localStorage.setItem('currentUser', JSON.stringify(data));
      });
    }
  }
}
