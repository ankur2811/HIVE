import { ComponentFixture, TestBed } from '@angular/core/testing';
import {HttpClientModule} from '@angular/common/http';
import { ViewprofileComponent } from './viewprofile.component';
import { RouterTestingModule } from '@angular/router/testing';

describe('ViewprofileComponent', () => {
  let component: ViewprofileComponent;
  let fixture: ComponentFixture<ViewprofileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule,RouterTestingModule],
      declarations: [ ViewprofileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewprofileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

 /* it('should create', () => {
    let data={
      id:1,
      profilePicture:"pqr"
    }
    component.user=data;
    localStorage.setItem('currentUser', JSON.stringify(data));
    localStorage.setItem('clickedUser', JSON.stringify(data));
    expect(component).toBeTruthy();
  });*/
});
