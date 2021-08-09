import { ComponentFixture, TestBed } from '@angular/core/testing';
import {HttpClientModule} from '@angular/common/http';
import { EditEventComponent } from './edit-event.component';
import { RouterTestingModule } from '@angular/router/testing';

let ls={
  description:"jhjh",
  date:"12-12T12:23"
}

describe('EditEventComponent', () => {
  let component: EditEventComponent;
  let fixture: ComponentFixture<EditEventComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule,RouterTestingModule],
      declarations: [ EditEventComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditEventComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

 /* it('should create', () => {
      fixture = TestBed.createComponent(EditEventComponent);
     component = fixture.componentInstance;
     component.List=ls;
     component.id=1;

     const app = fixture.debugElement.componentInstance;
     fixture.detectChanges();
    expect(app).toBeTruthy();
  });*/
});
