import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { finalize } from 'rxjs/operators';
import { AngularFireStorage } from '@angular/fire/storage';
import { DatePipe } from '@angular/common';
@Component({
  selector: 'app-create-event',
  templateUrl: './create-event.component.html',
  styleUrls: ['./create-event.component.scss'],
})
export class CreateEventComponent implements OnInit {
  http: HttpClient;
  isclicked = 0;
  newActivity = new FormGroup({
    title: new FormControl('', [Validators.required]),
    description: new FormControl('', [Validators.required]),
    tags: new FormControl([], [Validators.required]),
    date: new FormControl('', [Validators.required]),
    time: new FormControl('', [Validators.required]),
    activityPic: new FormControl(''),
  });
  img: String;
  selectedImage: any = null;
  flag: number;
  constructor(
    http: HttpClient,
    private storage: AngularFireStorage,
    private datePipe: DatePipe
  ) {
    this.http = http;
  }
  c_date;

  ngOnInit(): void {
    this.c_date = Date.now();
    this.c_date = this.datePipe.transform(this.c_date, 'yyyy-MM-dd');
    this.flag = 0;
    this.img = '../../../assets/download.png';
    this.isclicked = 0;
  }
  tagsList(n: number) {
    return [
      { id: 1, name: 'Technical' },
      { id: 2, name: 'Fun' },
      { id: 3, name: 'HU' },
      { id: 4, name: 'CSR' },
      { id: 5, name: 'HUEx' },
      { id: 6, name: 'HBS' },
      { id: 7, name: 'Sports' },
      { id: 8, name: 'Outing' },
      { id: 9, name: 'Wellness' },
    ];
  }
  onCreate() {
    let dt = this.newActivity.value.date + 'T' + this.newActivity.value.time;
    this.c_date = new Date();
    let dtime = new Date(dt);

    if (dtime <= this.c_date) {
      this.flag = 1;
    } else {
      this.flag = 0;

      this.isclicked = 1;

      let url = '../../../assets/default.jpg';

      let currentuser = JSON.parse(localStorage.getItem('currentUser'));
      if (this.selectedImage != null) {
        var filepath = `images/activity/${this.newActivity.value.title}_${
          currentuser.id
        }_${new Date().getTime()}`;
        const fileRef = this.storage.ref(filepath);
        this.storage
          .upload(filepath, this.selectedImage)
          .snapshotChanges()
          .pipe(
            finalize(() => {
              fileRef.getDownloadURL().subscribe((urlimg) => {
                url = urlimg;
                this.savedetails(url, currentuser);
              });
            })
          )
          .subscribe();
      } else {
        url = '../../../assets/default.jpg';
        this.savedetails(url, currentuser);
      }
    }
  }
  showPreview(event: any) {
    if (event.target.files && event.target.files[0]) {
      const reader = new FileReader();
      reader.onload = (e: any) => (this.img = e.target.result);
      reader.readAsDataURL(event.target.files[0]);
      this.selectedImage = event.target.files[0];
    } else {
      this.img = '';
      this.selectedImage = null;
    }
  }
  savedetails(url, currentuser) {
    let tag = this.newActivity.value.tags.join(',');
    let date = this.newActivity.value.date + 'T' + this.newActivity.value.time;
    let newActivity = {
      title: this.newActivity.value.title,
      description: this.newActivity.value.description,
      tags: tag,
      date: date,
      activityPic: url,
    };
    this.http
      .post(
        'https://hivebackend-dot-hu18-groupb-java.et.r.appspot.com/activity/add/' +
          currentuser.id,
        newActivity
      )
      .subscribe((data) => {
        window.location.reload();
      });
  }
}
