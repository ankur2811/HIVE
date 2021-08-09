import { DatePipe } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { UserActivityService } from 'src/app/user-activity.service';

@Component({
  selector: 'app-user-activity-history',
  templateUrl: './user-activity-history.component.html',
  styleUrls: ['./user-activity-history.component.scss'],
})
export class UserActivityHistoryComponent implements OnInit {
  userActivity:UserActivityService
  constructor(private datePipe: DatePipe, userActivity:UserActivityService) {
    this.userActivity=userActivity
  }
  @Input() List:any;
  @Input() id;
  @Input() userid;
  date;
  clickedUser

  ngOnInit(): void {
    let x=JSON.parse(localStorage.getItem('clickedUser'))
    if(x!=null)
    this.clickedUser=x.id
    this.date=  new Date()
  }
  get_date(date:string){
    let dt=new Date(date)
    return dt.toDateString()
    }
    formatdate(date:string){
      return new Date(date)
    }
}
