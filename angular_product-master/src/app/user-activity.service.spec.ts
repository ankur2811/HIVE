import { TestBed } from '@angular/core/testing';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { UserActivityService } from './user-activity.service';
import {
  HttpTestingController,
  HttpClientTestingModule,
} from '@angular/common/http/testing';
import { UserDataService } from 'src/app/userdata.service';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';


let userid=1;
let activityid=1;

let stubActivity={
  id:1,
  description:"abc",
  date:"12-12-1222",
  time:"12:12"
}

let data={
  Search:"abc",
  tab:"tab"
}

let stubData = {
  id: 1,
  username: 'testing',
  registered: ['abc'],
  invited: ['pqr'],
};
let id = 1;

class FakeUserDataService {
  get_user_byid(id) {
    return Observable.of(stubData);
  }
}

describe('UserActivityService', () => {
  let service: UserActivityService;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  let newFakeUserDataService = new FakeUserDataService();

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, HttpClientTestingModule],
      providers: [
        { provide: UserDataService, useValue: newFakeUserDataService },
      ],
    });
    service = TestBed.inject(UserActivityService);
    httpMock = TestBed.get(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('get_all_activities() should call http Get method for the given route', () => {
    //Arrange
    //Set Up Data
    const user = { userName: 'TheCodeBuzz', Id: '2131' };

    //Act
    service.get_all_activities().subscribe((emp) => {
      //Assert-1
      expect(emp).toEqual(user);
    });

    //Assert -2
    const req = httpMock.expectOne(
      'https://hivebackend-dot-hu18-groupb-java.et.r.appspot.com/activity/allactivities'
    );

    //Assert-3
    expect(req.request.method).toEqual('GET');

    //Assert-4
    req.flush(user);
    //Assert -5
    httpMock.verify();
  });

  it('get_activity_by_id() should call http Get method for the given route', () => {
    //Arrange
    //Set Up Data
    let id = 1;
    const user = { userName: 'TheCodeBuzz', Id: '2131' };

    //Act
    service.getActivity_by_id(id).subscribe((emp) => {
      //Assert-1
      expect(emp).toEqual(user);
    });

    //Assert -2
    const req = httpMock.expectOne(
      'https://hivebackend-dot-hu18-groupb-java.et.r.appspot.com/activity/' + id
    );

    //Assert-3
    expect(req.request.method).toEqual('GET');

    //Assert-4
    req.flush(user);
    //Assert -5
    httpMock.verify();
  });

  it('should able to call get_user_activity', () => {
    let data = {
      id: 1,
      username: 'ABC',
    };
    localStorage.setItem('currentUser', JSON.stringify(data));
    expect(service.get_user_activity).toBeTruthy();
    let registered = service.get_user_activity();
    expect(registered[0]).toEqual(stubData[0]);
  });

 /* it('should able to call get_registered_api', () => {
    let data = {
      id: 1,
      username: 'ABC',
    };
    localStorage.setItem('currentUser', JSON.stringify(data));
    expect(service.get_registered_api).toBeTruthy();
    service.get_registered_api();
    let registered = service.get_registered();
    expect(registered).toEqual(stubData.registered);
  });

  it('should able to call get_invited_api', () => {
    let data = {
      id: 1,
      username: 'ABC',
    };
    localStorage.setItem('currentUser', JSON.stringify(data));

    expect(service.get_invited_api).toBeTruthy();
    service.get_invited_api();
    let invited = service.get_invited();
    expect(invited).toEqual(stubData.invited);
  });*/


  it("should call POST API to post a review", () => {

    service.postreview("review",1,1).subscribe();

    const req = httpMock.expectOne(
      'https://hivebackend-dot-hu18-groupb-java.et.r.appspot.com/api/status/review/1/1'
    );

    //Assert-3
    expect(req.request.method).toEqual('POST');
    expect(req.request.body).toEqual('review');
  });


  it("should call PUT API to update a activity", () => {

    service.update_activity(1,stubActivity,'before_event');

    const req = httpMock.expectOne(
      'https://hivebackend-dot-hu18-groupb-java.et.r.appspot.com/activity/update/1'
    );

    //Assert-3
    expect(req.request.method).toEqual('PUT');

  });



  it('get_past_search(data) should call http Get method for the given route', () => {
    //Arrange
    //Set Up Data
    let id = 1;
    const user = { userName: 'TheCodeBuzz', Id: '2131' };

    //Act
    service.get_past_search(data).subscribe((emp) => {
      //Assert-1
      expect(emp).toEqual(user);
    });

    //Assert -2
    const req = httpMock.expectOne(
     'https://hivebackend-dot-hu18-groupb-java.et.r.appspot.com/activity/pastactivity/'+data.Search+'?searchParam='+data.tab
    );

    //Assert-3
    expect(req.request.method).toEqual('GET');

    //Assert-4
    req.flush(user);
    //Assert -5
    httpMock.verify();
  });



  it('get_total_past_activities() should call http Get method for the given route', () => {
    //Arrange
    //Set Up Data
    let id = 1;
    const user = { userName: 'TheCodeBuzz', Id: '2131' };

    //Act
    service.get_total_past_activities().subscribe((emp) => {
      //Assert-1
      expect(emp).toEqual(user);
    });

    //Assert -2
    const req = httpMock.expectOne(
     'https://hivebackend-dot-hu18-groupb-java.et.r.appspot.com/activity/countpast'
    );

    //Assert-3
    expect(req.request.method).toEqual('GET');

    //Assert-4
    req.flush(user);
    //Assert -5
    httpMock.verify();
  });



  it("remove_participted(userid, activityid) should call PUT API to update a activity", () => {

    service.remove_participted(userid, activityid);

    const req = httpMock.expectOne(
      'https://hivebackend-dot-hu18-groupb-java.et.r.appspot.com/api/status/'+userid+'/'+activityid+'/registered'
    );

    //Assert-3
    expect(req.request.method).toEqual('PUT');

  });



  it(" mark_participted(arr, activityid) should call PUT API to update a activity", () => {

    service. mark_participted("arr", activityid);

    const req = httpMock.expectOne(
      'https://hivebackend-dot-hu18-groupb-java.et.r.appspot.com/activity/markpresent/'+activityid
    );

    //Assert-3
    expect(req.request.method).toEqual('PUT');
    expect(req.request.body).toEqual("arr");

  });


  it('get_past_activities(p: any, itemscount: any) should call http Get method for the given route', () => {
    //Arrange
    //Set Up Data
    let id = 1;
    const user = { userName: 'TheCodeBuzz', Id: '2131' };

    //Act
    service.get_past_activities(1, 5).subscribe((emp) => {
      //Assert-1
      expect(emp).toEqual(user);
    });

    //Assert -2
    const req = httpMock.expectOne(
     'https://hivebackend-dot-hu18-groupb-java.et.r.appspot.com/activity/paging/?pageNo=1&pageSize=5'
    );

    //Assert-3
    expect(req.request.method).toEqual('GET');

    //Assert-4
    req.flush(user);
    //Assert -5
    httpMock.verify();
  });



});
