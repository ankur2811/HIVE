import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UserActivityService } from 'src/app/user-activity.service';

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.scss'],
})
export class ReviewComponent implements OnInit {
  reviews = [];
  tmpreviews = [];
  reviewform = new FormGroup({
    review: new FormControl('', [Validators.required]),
  });
  user: any;
  constructor(private activtyservice: UserActivityService) {}
  @Input() list: any;
  @Input() activity;
  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem('currentUser'));
    for (let act of this.list) {
      if (act.review != null) {
        let x = {
          id: act.id,
          name: act.name,
          profilePicture: act.profilePicture,
          review: act.review,
        };
        this.reviews.push(x);
      }
    }
  }
  post() {
    this.activtyservice
      .postreview(
        this.reviewform.value.review,
        this.user.id,
        this.activity.activityid
      )
      .subscribe(() => {
        let x = {
          id: this.user.id,
          name: this.user.name,
          profilePicture: this.user.profilePicture,
          review: this.reviewform.value.review,
        };

        this.reviews.push(x);
      });
  }
  find_participants() {
    let i = this.list.findIndex((activity) => {
      return activity.id === this.user.id;
    });
    return i;
  }
  find_reviews() {
    let i = this.reviews.findIndex((act) => {
      return this.user.id === act.id;
    });
    return i;
  }
  get_reviews() {
    return this.reviews;
  }
}
