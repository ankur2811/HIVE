import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
declare var Swal: any;

@Injectable({
  providedIn: 'root',
})
export class UserDataService {
  http: HttpClient;
  private serverurl = 'hivebackend-dot-hu18-groupb-java.et.r.appspot.com';

  constructor(http: HttpClient, private router: Router) {
    this.http = http;
  }
  private user;
  set_user(user: any) {
    this.user = user;
  }
  get_user() {
    return this.user;
  }

  get_user_api(Email: string, pass: string): Observable<any> {
    return this.http.get(
      'https://' +
        this.serverurl +
        '/api/login/email/' +
        Email +
        '/password/' +
        pass
    );
  }

  register_user(details: any) {
    return this.http

      .post('https://' + this.serverurl + '/api/login', details)
      .subscribe(
        (data) => {
          this.alertConfirmation('success', 'Successfully registered');
          this.router.navigate(['login']);
        },
        (error) => {
          this.alertConfirmation('warning', 'Email already exists !!');
        }
      );
  }

  get_user_byid(token): Observable<any> {
    let headers = new HttpHeaders();
    headers = headers.append('Authorization', 'Bearer ' + token);
    return this.http.get('https://' + this.serverurl + '/api/login/jwt/id', {
      headers: headers,
    });
  }
  get_user_by_id(id): Observable<any> {
    return this.http.get('https://' + this.serverurl + '/api/login/' + id);
  }

  update_user_api(id, user) {
    let token = JSON.parse(localStorage.getItem('token')).token;
    let headers = new HttpHeaders();
    headers = headers.append('Authorization', 'Bearer ' + token);
    this.http
      .put('https://' + this.serverurl + '/api/login/jwt/id', user, {
        headers: headers,
      })
      .subscribe(() => {});
  }

  update_user(id, userdetails) {
    var user = {
      bio: userdetails.bio,
      areaofinterest: userdetails.areaofinterest.join(','),
    };
    let currentuser = JSON.parse(localStorage.getItem('currentUser'));
    currentuser.bio = userdetails.bio;
    currentuser.tags = userdetails.areaofinterest.join(',');
    currentuser.tagList = userdetails.areaofinterest;
    localStorage.setItem('currentUser', JSON.stringify(currentuser));
    this.update_user_api(id, user);
  }
  update_password(id, userdetails) {
    var user = {
      password: userdetails.password,
    };
    this.update_user_api(id, user);
  }
  get_leaders(tab) {
    if (tab === 'perform')
      return this.http.get(
        'https://' +
          this.serverurl +
          '/api/login/paging?pageNo=0&pageSize=5&sortBy=score'
      );
    else
      return this.http.get(
        'https://' +
          this.serverurl +
          '/api/login/paging?pageNo=0&pageSize=5&sortBy=count'
      );
  }
  get_year_wise_count() {
    return this.http.get(`https://${this.serverurl}/activity/yearwisecount`);
  }
  get_tag_wise_count() {
    return this.http.get(`https://${this.serverurl}/activity/tagwisecount`);
  }
  forget_pass(email) {
    return this.http.get(`https://${this.serverurl}/api/login/forget/${email}`);
  }
  alertConfirmation(icon, text) {
    Swal.fire({
      text: text,
      icon: icon,
      showCancelButton: false,
      confirmButtonText: 'Ok',
      cancelButtonText: 'No',
    }).then((result) => {
      if (icon != 'success') {
        window.location.reload();
      }
    });
  }
}
