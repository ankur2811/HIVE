import { Component, OnInit } from '@angular/core';
import { UserDataService } from 'src/app/userdata.service';

@Component({
  selector: 'app-stats',
  templateUrl: './stats.component.html',
  styleUrls: ['./stats.component.scss'],
})
export class StatsComponent implements OnInit {
  datasetyear;
  chartlabyear = [];
  datalabyear = [];
  datasettag;
  chartlabtag = [];
  datalabtag = [];

  constructor(private userData: UserDataService) {}

  ngOnInit(): void {
    this.userData.get_year_wise_count().subscribe((data) => {
      this.datasetyear = data;
      this.datasetyear.sort(this.sortFunction);
      for (let i of this.datasetyear) {
        console.log(i);
        this.chartlabyear.push(i[0]);
        this.datalabyear.push(i[1]);
      }
    });
    this.userData.get_tag_wise_count().subscribe((data) => {
      this.datasettag = data;
      for (let keys of Object.keys(this.datasettag)) {
        if (
          keys != 'total' &&
          keys != 'totaluser' &&
          keys != 'totalactivities' &&
          keys != 'totaluser' &&
          keys != 'top activity' &&
          keys != 'totalactive'
        ) {
          this.chartlabtag.push(keys);
          this.datalabtag.push(this.datasettag[keys]);
        }
      }
    });
  }
  sortFunction(a, b) {
    if (a[0] === b[0]) {
      return 0;
    } else {
      return a[0] < b[0] ? -1 : 1;
    }
  }
}
