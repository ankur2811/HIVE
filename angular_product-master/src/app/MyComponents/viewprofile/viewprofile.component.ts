import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { UserActivityService } from 'src/app/user-activity.service';
import { UserDataService } from 'src/app/userdata.service';

@Component({
  selector: 'app-viewprofile',

  templateUrl: './viewprofile.component.html',

  styleUrls: ['./viewprofile.component.scss'],
})
export class ViewprofileComponent implements OnInit {
  userdetails;
  userdata: UserDataService;
  currentUser;
  userActivity: UserActivityService;
  userdetails$: Observable<any>;
  activatedroute: ActivatedRoute;
  clickedId;
  currentId;
  tab = 1;
  profileform = new FormGroup({
    bio: new FormControl(''),
    areaofinterest: new FormControl(''),
  });

  items = [
    'Technical',
    'Fun',
    'HU',
    'HUEx',
    'CSR',
    'HBS',
    'Sports',
    'Outing',
    'Wellness',
  ];
  constructor(
    userdata: UserDataService,
    activatedroute: ActivatedRoute,
    userActivity: UserActivityService
  ) {
    this.userdata = userdata;
    this.userActivity = userActivity;
    activatedroute.params.subscribe((data) => {
      this.userdetails = data.user;
    });
  }
  @Input() user;
  ngOnInit(): void {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    if (this.user.id === this.currentUser.id) {
      this.user = this.currentUser;
    }
    this.currentId = JSON.parse(localStorage.getItem('currentUser')).id;
    this.clickedId = JSON.parse(localStorage.getItem('clickedUser')).id;
    this.userdetails = this.currentUser;
    this.profileform.setValue({
      bio: this.currentUser.bio,
      areaofinterest: this.currentUser.tagList,
    });
  }

  getSelectedValue() {
    this.userdata.update_user(this.user.id, this.profileform.value);

    this.tab = 1;
    this.ngOnInit();
  }

  toggle() {
    this.tab = 0;
  }
}
