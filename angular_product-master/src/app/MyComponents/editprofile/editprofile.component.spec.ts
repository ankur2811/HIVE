import { ComponentFixture, TestBed } from '@angular/core/testing';
import {HttpClientModule} from '@angular/common/http';
import { EditprofileComponent } from './editprofile.component';
import { RouterTestingModule } from '@angular/router/testing';

describe('EditprofileComponent', () => {
  let component: EditprofileComponent;
  let fixture: ComponentFixture<EditprofileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule,RouterTestingModule],
      declarations: [ EditprofileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditprofileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

 /* it('should create', () => {
    let data={
      id:1,
      username:"ABC",
      bio:"jgghh",
      areaofinterest:"tag1,tag2"
    }
    localStorage.setItem('currentUser', JSON.stringify(data));
   // expect(component).toBeTruthy();
  });*/
});
