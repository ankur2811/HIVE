import { ApplicationRef, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserActivityService } from 'src/app/user-activity.service';
import { UserDataService } from 'src/app/userdata.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit {
  task$;
  user;
  organized = [];
  participated = [];
  activities: UserActivityService;
  userdata: UserDataService;
  router;
  clickedId;
  currentId;
  userdetails: any;

  constructor(
    activities: UserActivityService,
    userdata: UserDataService,
    router: Router,
    private ar: ApplicationRef
  ) {
    this.userdata = userdata;
    this.activities = activities;
    this.router = router;
  }

  ngOnInit(): void {
    this.currentId = JSON.parse(localStorage.getItem('currentUser')).id;
    this.clickedId = JSON.parse(localStorage.getItem('clickedUser')).id;

    let id = JSON.parse(localStorage.getItem('clickedUser'));
    if (id != null) {
      this.userdata.get_user_by_id(id.id).subscribe((data) => {
        this.user = data;
        this.organized = data.organised;
        this.participated = data.participatedactivities;
      });
    }
  }
}
