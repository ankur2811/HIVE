import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Workbook } from 'exceljs';
import { Observable } from 'rxjs';
import { UserActivityService } from 'src/app/user-activity.service';
import * as fs from 'file-saver';
import { DatePipe } from '@angular/common';
import '@angular/localize/init';

@Component({
  selector: 'app-activitypage',
  templateUrl: './activitypage.component.html',
  styleUrls: ['./activitypage.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class ActivitypageComponent implements OnInit {
  current;
  activityservice: UserActivityService;
  activityid;
  activity$: Observable<any>;
  participated;
  registered;
  all = [];
  attendees = [];
  date;

  constructor(
    activityservice: UserActivityService,
    activatedroute: ActivatedRoute,
    private datePipe: DatePipe
  ) {
    this.activityservice = activityservice;
    activatedroute.params.subscribe((data) => {
      this.activityid = data.activityid;
    });
  }

  ngOnInit(): void {
    this.current = JSON.parse(localStorage.getItem('currentUser'));
    this.activity$ = this.activityservice.getActivity_by_id(this.activityid);
    this.activity$.subscribe((data) => {
      console.log(data);
      if (data.attendenceMarked === false) {
        this.all = data.registered;
      } else {
        this.all = data.participated;
      }
      this.participated = data.participated;
    });

    this.date = new Date();
  }
  formatdate(date: string) {
    return new Date(date);
  }
  mark_present(user) {
    let indx = this.attendees.findIndex((x) => x === user.id);
    let ind = this.participated.findIndex((x) => x.id === user.id);
    if (indx === -1) {
      this.attendees.push(user.id);
      this.participated.push(user);
    } else {
      this.attendees.splice(indx, 1);

      this.participated.splice(indx, 1);
    }
  }
  is_marked(participant) {
    return this.participated.findIndex((x) => x.id === participant.id);
  }

  onMark(activityid) {
    this.activityservice.mark_participted(this.attendees, activityid);
    this.ngOnInit();
  }
  exportExcel(title) {
    let workbook = new Workbook();
    let worksheet = workbook.addWorksheet('ParticipantSheet');
    worksheet.columns = [
      { header: 'Name', key: 'name', width: 32 },
      { header: 'Username', key: 'username', width: 32 },
    ];

    this.all.forEach((e) => {
      worksheet.addRow({ name: e.name, username: e.username }, 'n');
    });

    workbook.xlsx.writeBuffer().then((data) => {
      let blob = new Blob([data], {
        type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
      });
      fs.saveAs(blob, `${title}.xlsx`);
    });
  }
  setClickedProfile(id, pic) {
    let clickedUser = { id: id, profilePicture: pic };
    localStorage.setItem('clickedUser', JSON.stringify(clickedUser));
  }
}
