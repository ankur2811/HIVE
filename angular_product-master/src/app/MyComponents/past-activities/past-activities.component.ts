import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { UserActivityService } from 'src/app/user-activity.service';

@Component({
  selector: 'app-past-activities',
  templateUrl: './past-activities.component.html',
  styleUrls: ['./past-activities.component.scss'],
})
export class PastActivitiesComponent implements OnInit {
  p: number;
  page;
  activities;
  total;
  userActivity: UserActivityService;
  searchResult$: Observable<any>;
  searchBar = new FormGroup({
    Search: new FormControl('', [Validators.required]),
    tab: new FormControl('title', [Validators.required]),
  });

  constructor(userActivity: UserActivityService) {
    this.userActivity = userActivity;
  }

  ngOnInit(): void {
    this.page = 1;
    this.userActivity.get_total_past_activities().subscribe((data) => {
      this.total = data;
    });
    this.userActivity
      .get_past_activities(this.page - 1, 10)
      .subscribe((data) => {
        this.activities = data;
      });
  }

  handlePageChange(event) {
    if (event != null) this.page = event;
    else this.page = 1;
    this.userActivity
      .get_past_activities(this.page - 1, 10)
      .subscribe((data) => {
        this.activities = data;
      });
  }
  OnSearch() {
    this.searchResult$ = this.userActivity.get_past_search(
      this.searchBar.value
    );
  }
}
