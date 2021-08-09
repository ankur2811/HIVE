import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserActivityHistoryComponent } from './user-activity-history.component';

describe('UserActivityHistoryComponent', () => {
  let component: UserActivityHistoryComponent;
  let fixture: ComponentFixture<UserActivityHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserActivityHistoryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserActivityHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

 // it('should create', () => {

    //expect(component).toBeTruthy();
  //});
});
