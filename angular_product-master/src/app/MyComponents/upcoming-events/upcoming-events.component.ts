import { Component, Input, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { UserActivityService } from 'src/app/user-activity.service';
import { UserDataService } from 'src/app/userdata.service';

@Component({
  selector: 'app-upcoming-events',
  templateUrl: './upcoming-events.component.html',
  styleUrls: ['./upcoming-events.component.scss'],
})
export class UpcomingEventsComponent implements OnInit {
  userActivity: UserActivityService;
  userdata: UserDataService;
  activities$: Observable<any>;
  constructor(userActivity: UserActivityService, userdata: UserDataService) {
    this.userActivity = userActivity;
    this.userdata = userdata;
  }

  ngOnInit(): void {
    this.userActivity.get_registered_api();
  }
  get_countdown(datetime) {
    var countDownDate = new Date(datetime).getTime();

    // Update the count down every 1 second

    // Get today's date and time
    var now = new Date().getTime();

    // Find the distance between now and the count down date
    var distance = countDownDate - now;

    // Time calculations for days, hours, minutes and seconds
    var days = Math.floor(distance / (1000 * 60 * 60 * 24));
    var hours = Math.floor(
      (distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
    );
    var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
    var seconds = Math.floor((distance % (1000 * 60)) / 1000);

    // Display the result in the element with id="demo"
    if (days <= 0) {
      if (hours === 0) return minutes + ' min';
      else if (hours < 0) return '';
      else return hours + ' hrs ';
    } else return days + 'days ' + hours + 'hrs ';
  }
}
