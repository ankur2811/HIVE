import { Component, Input, OnInit } from '@angular/core';
import { UserDataService } from 'src/app/userdata.service';
import { Router } from '@angular/router';
import { finalize } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AngularFireStorage } from '@angular/fire/storage';

@Component({
  selector: 'app-editprofile',
  templateUrl: './editprofile.component.html',
  styleUrls: ['./editprofile.component.scss'],
})
export class EditprofileComponent implements OnInit {
  isclicked = false;
  img: string;
  selectedImage: any = null;
  clicked;
  current;
  private serverurl = 'hivebackend-dot-hu18-groupb-java.et.r.appspot.com';
  constructor(
    private userData: UserDataService,
    private router: Router,
    private storage: AngularFireStorage,
    private http: HttpClient
  ) {}
  @Input() user;
  ngOnInit(): void {
    this.clicked = JSON.parse(localStorage.getItem('clickedUser'));
    this.current = JSON.parse(localStorage.getItem('currentUser'));
    if (this.clicked.id === this.current.id) {
      this.img = this.current.profilePicture;
    } else {
      this.img = this.clicked.profilePicture;
    }
  }
  onclick() {
    this.router.navigate(['action-selection'], { state: { example: 'bar' } });
  }
  showPreview(event: any) {
    this.isclicked = true;
    if (event.target.files && event.target.files[0]) {
      const reader = new FileReader();
      reader.onload = (e: any) => (this.img = e.target.result);
      reader.readAsDataURL(event.target.files[0]);
      this.selectedImage = event.target.files[0];
    } else {
      this.img = this.user.profilePicture;
      this.selectedImage = null;
    }
    this.isclicked = false;
  }
  getSelectedValueimg() {
    if (this.selectedImage != null) {
      var filepath = `images/users/${this.user.name}_${this.user.username}_${this.user.id}`;
      const fileRef = this.storage.ref(filepath);
      this.storage
        .upload(filepath, this.selectedImage)
        .snapshotChanges()
        .pipe(
          finalize(() => {
            fileRef.getDownloadURL().subscribe((url) => {
              let token = JSON.parse(localStorage.getItem('token')).token;
              let headers = new HttpHeaders();
              headers = headers.append('Authorization', 'Bearer ' + token);
              let user = { profilePicture: url };
              this.http
                .put('https://' + this.serverurl + '/api/login/jwt/id', user, {
                  headers: headers,
                })
                .subscribe(() => {});

              this.current.profilePicture = url;
              localStorage.setItem('currentUser', JSON.stringify(this.current));
            });
          })
        )
        .subscribe();
    }
  }

  close() {
    this.img = this.user.profilePicture;
    this.selectedImage = null;
    this.ngOnInit();
  }
  click(event: any) {
    event.target.value = null;
  }
}
