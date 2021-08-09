import { NgModule,CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './MyComponents/login/login.component';
import { NavbarComponent } from './MyComponents/navbar/navbar.component';
import { SignupComponent } from './MyComponents/signup/signup.component';
import { UserDataService } from './userdata.service';
import { LandingpageComponent } from './MyComponents/landingpage/landingpage.component';
import { ProfileComponent } from './MyComponents/profile/profile.component';
import { HomepageComponent } from './MyComponents/homepage/homepage.component';
import { InvitesComponent } from './MyComponents/invites/invites.component';
import { UpcomingEventsComponent } from './MyComponents/upcoming-events/upcoming-events.component';
import { PostsComponent } from './MyComponents/posts/posts.component';
import { CreateEventComponent } from './MyComponents/create-event/create-event.component';
import { ViewprofileComponent } from './MyComponents/viewprofile/viewprofile.component';
import { EditprofileComponent } from './MyComponents/editprofile/editprofile.component';
import { FormsModule } from '@angular/forms';
import { NgSelectModule } from '@ng-select/ng-select';
import { UserActivityHistoryComponent } from './MyComponents/user-activity-history/user-activity-history.component';
import { MainscreenComponent } from './MyComponents/mainscreen/mainscreen.component';
import {NgxPaginationModule} from 'ngx-pagination';
import { ActivitypageComponent } from './MyComponents/activitypage/activitypage.component'
import{ BnNgIdleService } from 'bn-ng-idle';
import {AngularFireModule} from '@angular/fire'
import {AngularFireStorageModule} from '@angular/fire/storage'
import {DatePipe} from '@angular/common';
import { PastActivitiesComponent } from './MyComponents/past-activities/past-activities.component';
import { LeadersboardComponent } from './MyComponents/leadersboard/leadersboard.component';
import { GraphComponent } from './MyComponents/graph/graph.component';
import { ChartsModule } from 'ng2-charts';
import { StatsComponent } from './MyComponents/stats/stats.component';
import { EditEventComponent } from './MyComponents/edit-event/edit-event.component';
import { ReviewComponent } from './MyComponents/review/review.component';
import { DropzoneDirective } from './dropzone.directive';
import { UploaderComponent } from './MyComponents/uploader/uploader.component';
import { UploadTaskComponent } from './MyComponents/upload-task/upload-task.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
const routes = [
  {
    path: '',
    component: LandingpageComponent,
    children: [
      { path: '', redirectTo: 'login', pathMatch: 'full' },
      { path: 'login', component: LoginComponent },
      { path: 'register', component: SignupComponent },
    ],
  },
  {path:'mainscreen',component:MainscreenComponent,
  children:[{ path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomepageComponent },
  {path:'invited',component:InvitesComponent},
  {path:'upcoming',component:UpcomingEventsComponent},
  {path:'past-activities',component:PastActivitiesComponent},
  {
    path: 'profile',
    component: ProfileComponent,
    children: [
      { path: '', redirectTo: 'view', pathMatch: 'full', },
      { path: 'view', component: ViewprofileComponent },
      { path: 'edit', component: EditprofileComponent },
      { path: 'participated', component: UserActivityHistoryComponent },
      { path: 'organized', component: UserActivityHistoryComponent },
    ],
  },
  {​​​​​​​​path:'stats', component: StatsComponent}​​​​​​​​,
  { path: 'activity/:activityid', component: ActivitypageComponent },
  ]},

  {path:'**', redirectTo: 'login' }
];
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NavbarComponent,
    SignupComponent,
    LandingpageComponent,
    ProfileComponent,
    HomepageComponent,
    InvitesComponent,
    UpcomingEventsComponent,
    PostsComponent,
    CreateEventComponent,
    ViewprofileComponent,
    EditprofileComponent,
    UserActivityHistoryComponent,
    MainscreenComponent,
    PastActivitiesComponent,
    ActivitypageComponent,
    LeadersboardComponent,
    StatsComponent,
    GraphComponent,
    EditEventComponent,
    ReviewComponent,
    DropzoneDirective,
    UploaderComponent,
    UploadTaskComponent,
  ],
  imports: [
    BrowserModule,
    ChartsModule,
    FormsModule,
    NgbModule,
    ReactiveFormsModule,
    NgSelectModule,
    HttpClientModule,
    RouterModule.forRoot(routes,{onSameUrlNavigation:'reload'}),
    NgxPaginationModule,
    AngularFireModule.initializeApp({
      apiKey: "AIzaSyDhwkP3og9P9eJAIPingPyCAs-MTxOrTkc",
      authDomain: "hive-263ae.firebaseapp.com",
      databaseURL: "https://hive-263ae-default-rtdb.firebaseio.com",
      projectId: "hive-263ae",
      storageBucket: "hive-263ae.appspot.com",
      messagingSenderId: "90883612127",
      appId: "1:90883612127:web:c1cc442cef8f5388d2386a",
      measurementId: "G-6D2G3GFDL5"

  }),
    AngularFireStorageModule,
  ],
  providers: [UserDataService,BnNgIdleService,DatePipe],
  bootstrap: [AppComponent],
  schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class AppModule {}
