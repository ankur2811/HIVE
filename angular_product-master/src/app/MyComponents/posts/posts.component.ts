import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { UserDataService } from 'src/app/userdata.service';
import { UserActivityService } from 'src/app/user-activity.service';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss'],
})
export class PostsComponent implements OnInit {
  userdata: UserDataService;
  activities$: Observable<any>;
  http;
  feed = [];
  feed1 = [];
  userActivity: UserActivityService;
  curentuser;
  srch: any;
  issearch = false;
  searchby = 'title';
  x = false;
  res;
  datepipe;

  constructor(
    userdata: UserDataService,
    httpclient: HttpClient,
    userActivity: UserActivityService
  ) {
    this.userdata = userdata;
    this.http = httpclient;
    this.userActivity = userActivity;
  }

  ngOnInit(): void {
    this.curentuser = JSON.parse(localStorage.getItem('currentUser'));
    this.get_activity_api();
  }
  get_activity_api() {
    this.userActivity.get_all_activities().subscribe((data) => {
      this.feed = data;
      this.feed1 = data;
      this.x = true;
    });
  }
  rev_sort(): any {
    throw new Error('Method not implemented.');
  }
  Join_event(activity) {
    this.userActivity.Accept_reject_invite(activity, 'join');
  }
  present_in_list(activity) {
    return this.userActivity.findIndexList(
      activity,
      this.userActivity.get_registered()
    );
  }
  compare(a, b) {
    if (a.activityid > b.activityid) return -1;
    else if (a.activityid < b.activityid) return 1;
    else return 0;
  }
  setClickedProfile(id, pic) {
    let clickedUser = { id: id, profilePicture: pic };
    localStorage.setItem('clickedUser', JSON.stringify(clickedUser));
  }
  setSearchBy(str: string) {
    this.searchby = str;
  }
  search() {
    this.issearch = true;
    if (this.srch !== undefined && this.srch != '') {
      this.feed = this.feed1;

      if (this.searchby === 'title') {
        this.feed = this.feed.filter((res) => {
          return res.title.toLowerCase().match(this.srch.toLowerCase());
        });
      } else {
        this.feed = this.feed.filter((res) => {
          return res.tags.toLowerCase().match(this.srch.toLowerCase());
        });
      }

      if (this.feed.length === 0) this.res = false;
    } else {
      this.feed = this.feed1;
    }
    this.issearch = false;
  }
}
