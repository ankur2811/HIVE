import { Component, Input, OnInit } from '@angular/core';
import { UserDataService } from 'src/app/userdata.service';

@Component({
  selector: 'app-leadersboard',
  templateUrl: './leadersboard.component.html',
  styleUrls: ['./leadersboard.component.scss'],
})
export class LeadersboardComponent implements OnInit {
  leaderBoard;
  constructor(private userService: UserDataService) {}
  @Input() tab;
  ngOnInit(): void {
    this.userService.get_leaders(this.tab).subscribe((data) => {
      this.leaderBoard = data;
    });
  }
  setClickedProfile(id, pic) {
    let clickedUser = { id: id, profilePicture: pic };
    localStorage.setItem('clickedUser', JSON.stringify(clickedUser));
  }
}
