import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserDataService } from './userdata.service';

@Injectable({
  providedIn: 'root',
})
export class UserActivityService {
  mp: Map<string, string> = new Map();

  private reviews = [];
  private aftereventPics = [];
  private registered = [];
  private invited = [];
  private recent = [];
  http: HttpClient;
  userdata: UserDataService;
  private serverurl = 'hivebackend-dot-hu18-groupb-java.et.r.appspot.com';
  set_colour() {
    this.mp.set('Technical', '#47597E');
    this.mp.set('Fun', '#FA877F');
    this.mp.set('HU', '#A1CAE2');
    this.mp.set('HUEx', '#3C415C');
    this.mp.set('CSR', '#95E1D3');
    this.mp.set('HBS', '#77ACF1');
    this.mp.set('Sports', '#FFCF63');
    this.mp.set('Outing', '#EEB76B');
    this.mp.set('Wellness', '#A6D6D6');
  }

  set_review(review) {
    this.reviews = review;
  }
  append_review(x) {
    this.reviews.push(x);
  }
  get_reviews() {
    return this.reviews;
  }
  constructor(http: HttpClient, userdata: UserDataService) {
    this.http = http;
    this.userdata = userdata;
  }
  set_aftereventPics(aftereventPics) {
    this.aftereventPics = aftereventPics;
  }
  append_aftereventPics(x) {
    this.aftereventPics.push(x);
  }
  get_aftereventPics() {
    return this.aftereventPics;
  }
  get_registered() {
    return this.registered;
  }
  get_recent() {
    return this.recent;
  }
  get_invited() {
    return this.invited;
  }

  get_recent_api() {
    let x = this.get_recent_list();
    x.subscribe((data) => {
      this.recent = data.reverse();
    });
    return x;
  }
  get_recent_list(): Observable<any> {
    let id = JSON.parse(localStorage.getItem('currentUser')).id;
    return this.http.get(
      'https://' + this.serverurl + '/activity/recentparticipated/' + id
    );
  }
  get_registered_api() {
    let x = this.get_registered_list();
    x.subscribe((data) => {
      this.registered = data.reverse();
    });
    return x;
  }

  get_user_activity() {
    let id = JSON.parse(localStorage.getItem('currentUser')).id;
    return this.userdata.get_user_byid(id);
  }

  get_invited_api() {
    let x = this.get_invited_list();
    x.subscribe((data) => {
      this.invited = data.reverse();
    });
  }
  get_invited_list(): Observable<any> {
    let headers = new HttpHeaders();
    let token = JSON.parse(localStorage.getItem('token')).token;
    headers = headers.append('Authorization', 'Bearer ' + token);
    return this.http.get(
      'https://' + this.serverurl + '/api/login/jwt/invited/id',
      { headers: headers }
    );
  }
  get_registered_list(): Observable<any> {
    let headers = new HttpHeaders();
    let token = JSON.parse(localStorage.getItem('token')).token;
    headers = headers.append('Authorization', 'Bearer ' + token);
    return this.http.get(
      'https://' + this.serverurl + '/api/login/jwt/registered/id',
      { headers: headers }
    );
  }

  findIndexList(data, list) {
    let i = list.findIndex((activity) => {
      return activity.activityid === data.activityid;
    });
    return i;
  }

  getActivity_by_id(activity_id): Observable<any> {
    return this.http.get(
      'https://' + this.serverurl + '/activity/' + activity_id
    );
  }

  Accept_reject_invite(activity, mode: string) {
    let userid = JSON.parse(localStorage.getItem('currentUser')).id;
    let i = this.findIndexList(activity, this.invited);
    //accept invite
    if (mode === 'accept') {
      let v = this.findIndexList(activity, this.registered);
      if (v === -1 && userid != activity.creatorid) {
        this.http
          .put(
            'https://' +
              this.serverurl +
              '/api/status/' +
              userid +
              '/' +
              activity.activityid +
              '/registered',
            ''
          )
          .subscribe((data) => {
            this.registered.push(activity);
            if (i != -1) {
              this.invited.splice(i, 1);
            }
          });
      }
    }
    //reject invite
    else if (mode === 'reject') {
      this.http
        .put(
          'https://' +
            this.serverurl +
            '/api/status/' +
            userid +
            '/' +
            activity.activityid +
            '/cancelled',
          ''
        )
        .subscribe((data) => {
          this.invited.splice(i, 1);
        });
    }
    //join using dashboard
    else {
      this.http
        .put(
          'https://' +
            this.serverurl +
            '/api/status/' +
            userid +
            '/' +
            activity.activityid +
            '/registered',
          ''
        )
        .subscribe((data) => {
          let v = this.findIndexList(activity, this.registered);
          if (v === -1 && userid != activity.creatorid) {
            this.registered.push(activity);
            let i = this.findIndexList(activity, this.invited);
            if (i != -1) {
              this.invited.splice(i, 1);
            }
          }
        });
    }
  }

  get_all_activities(): Observable<any> {
    return this.http.get(
      'https://' + this.serverurl + '/activity/allactivities'
    );
  }
  get_past_activities(p: any, itemscount: any) {
    return this.http.get<any>(
      'https://' +
        this.serverurl +
        '/activity/paging/?pageNo=' +
        p +
        '&pageSize=' +
        itemscount
    );
  }

  mark_participted(arr, activityid) {
    this.http
      .put(
        'https://' + this.serverurl + '/activity/markpresent/' + activityid,
        arr
      )
      .subscribe();
  }
  remove_participted(userid, activityid) {
    this.http
      .put(
        'https://' +
          this.serverurl +
          '/api/status/' +
          userid +
          '/' +
          activityid +
          '/registered',
        ''
      )
      .subscribe();
  }
  get_total_past_activities() {
    return this.http.get('https://' + this.serverurl + '/activity/countpast');
  }
  get_date(date: string) {
    let dt = new Date(date);
    return dt.toDateString();
  }

  tConvert(time) {
    // Check correct time format and split into components
    time = time
      .toString()
      .match(/^([01]\d|2[0-3])(:)([0-5]\d)(:[0-5]\d)?$/) || [time];

    if (time.length > 1) {
      // If time format correct
      time = time.slice(1); // Remove full string match value
      time[5] = +time[0] < 12 ? 'AM' : 'PM'; // Set AM/PM
      time[0] = +time[0] % 12 || 12; // Adjust hours
    }
    return time.join(''); // return adjusted time or original string
  }
  get_past_search(data): Observable<any> {
    return this.http.get<any>(
      `https://${this.serverurl}/activity/pastactivity/${data.Search}?searchParam=${data.tab}`
    );
  }
  update_activity(activityid, activity, tab) {
    var updatedactivity;
    if (tab === 'before_event') {
      updatedactivity = {
        description: activity.description,
        date: activity.date + 'T' + activity.time,
      };
    } else {
      updatedactivity = {
        morePics: this.aftereventPics.join(','),
      };
    }
    this.http
      .put(
        `https://${this.serverurl}/activity/update/${activityid}`,
        updatedactivity
      )
      .subscribe((data) => window.location.reload());
  }
  postreview(value: any, userid, activityid) {
    return this.http.post(
      `https://${this.serverurl}/api/status/review/${userid}/${activityid}`,
      value
    );
  }
}
