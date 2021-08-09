import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserDataService } from 'src/app/userdata.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss'],
})
export class HomepageComponent implements OnInit {
  token=null
  user = null;
  userdata: UserDataService;
  router:Router
  constructor(userdata: UserDataService,router:Router) {
    this.userdata = userdata;
    this.router=router
  }
  @Input() tab='home'
  ngOnInit(): void {
    this.user=JSON.parse(localStorage.getItem('currentUser'))
    this.token=JSON.parse(localStorage.getItem('token'))
    if(this.token===null){
      this.router.navigate([''])
    }
  }
  setClickedProfile(id,pic){
    let clickedUser={'id':id,'profilePicture':pic}
    localStorage.setItem("clickedUser",JSON.stringify(clickedUser))
  }
}
