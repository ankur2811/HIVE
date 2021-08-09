import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { UserActivityService } from 'src/app/user-activity.service';
import { UserDataService } from 'src/app/userdata.service';

@Component({
  selector: 'app-invites',
  templateUrl: './invites.component.html',
  styleUrls: ['./invites.component.scss'],
})
export class InvitesComponent implements OnInit {
  invited$:Observable<any>
  userActivity: UserActivityService;
  userdata: UserDataService;
  list=[]
  id
  private serverurl="hivebackend-dot-hu18-groupb-java.et.r.appspot.com"
  recent=[];
  date

  constructor(userActivity: UserActivityService, userdata: UserDataService,private http:HttpClient) {
    this.userActivity = userActivity;
    this.userdata = userdata;
  }
  @Input() tab='invite'
  ngOnInit(): void {


    this.id=JSON.parse(localStorage.getItem('currentUser')).id
    if(this.tab==='invite')
    this.userActivity.get_invited_api();
    else
    {
      this.userActivity.get_recent_api()
    }
  }

  On_Accepting(activity) {
    this.userActivity.Accept_reject_invite(activity, 'accept');
  }
  On_Rejecting(activity) {
    this.userActivity.Accept_reject_invite(activity, 'reject');
  }
}
