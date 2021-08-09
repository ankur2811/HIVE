import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { BnNgIdleService } from 'bn-ng-idle';
import { UserDataService } from './userdata.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  user=null
  constructor(private router:Router,private bnIdle: BnNgIdleService, private userdata: UserDataService) {

  }
  title = 'HIVE';
  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem('currentUser'));
    this.bnIdle.startWatching(1800).subscribe((isTimedOut: boolean) => {
      if (isTimedOut) {
        console.log('session expired');
        this.userdata.alertConfirmation('warning','Session timed out Please login again')
        localStorage.clear()
        this.router.navigate([''])
      }
    });
  }
}
