import { DatePipe } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { UserActivityService } from 'src/app/user-activity.service';

@Component({
  selector: 'app-edit-event',
  templateUrl: './edit-event.component.html',
  styleUrls: ['./edit-event.component.scss']
})
export class EditEventComponent implements OnInit {
  updateActivity = new FormGroup({
    description: new FormControl(''),
    date: new FormControl(''),
    time: new FormControl(''),
  });
  c_date

  @Input() List;
  @Input() id;
  isclicked=0;
  flag: number;


  constructor(private activityservice:UserActivityService, private datePipe:DatePipe) { }

  ngOnInit(): void {
    this.c_date=Date.now()
    this.c_date=this.datePipe.transform(this.c_date,"yyyy-MM-dd")
    this.flag=0
    this.isclicked=0;
    this.updateActivity.setValue({
      description: this.List.description,
      date: this.List.date.split("T")[0],
      time: this.List.date.split("T")[1]
  })
  }
  update(tab){
    let dt=this.updateActivity.value.date + 'T' + this.updateActivity.value.time;
    this.c_date=new Date();
    let dtime=new Date(dt)

    if(dtime<=this.c_date && tab==='before_event'){
      this.flag=1
    }
    else{
    this.flag=0
    this.isclicked=1;
    this.activityservice.update_activity(this.List.activityid,this.updateActivity.value,tab)
    }
  }
  close(){
    this.ngOnInit()
  }
}
