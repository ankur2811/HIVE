import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AngularFireAuth } from '@angular/fire/auth';
import * as firebase from 'firebase/app';
import { AngularFireModule } from '@angular/fire';
import { AngularFireDatabaseModule } from '@angular/fire/database';
import { UploadTaskComponent } from './upload-task.component';

describe('UploadTaskComponent', () => {
  let component: UploadTaskComponent;
  let fixture: ComponentFixture<UploadTaskComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        //AngularFireModule.initializeApp(yourFirebaseConfig),
        AngularFireDatabaseModule,
    ],
      declarations: [ UploadTaskComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UploadTaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

 /* it('should create', () => {
    expect(component).toBeTruthy();
  });*/
});
