import { TestBed } from '@angular/core/testing';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import { UserDataService } from './userdata.service';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';


let Email:"abc@gmail.com";
let pass:"pqr";
let id:1;

describe('UserDataService', () => {
  let service: UserDataService;
  let httpMock:HttpTestingController;
  let httpClient:HttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule,HttpClientTestingModule,RouterTestingModule ],
    });
    service = TestBed.inject(UserDataService);
    httpMock = TestBed.get(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('get_user_api() should call http Get method for the given route', () => {

    //Arrange
    //Set Up Data
   const user = {userName :'TheCodeBuzz', Id: '2131'}

    //Act
    service.get_user_api(Email,pass).subscribe((emp)=>{
      //Assert-1
      expect(emp).toEqual(user);

    });

    //Assert -2
    const req = httpMock.expectOne('https://hivebackend-dot-hu18-groupb-java.et.r.appspot.com/api/login/email/' + Email + '/password/' + pass);


     //Assert-3
     expect(req.request.method).toEqual("GET");

     //Assert-4
     req.flush(user);
    //Assert -5
    httpMock.verify();

  });


  it('get_user_byid() should call http Get method for the given route', () => {

    //Arrange
    //Set Up Data
   const user = {userName :'TheCodeBuzz', Id: '2131'}

    //Act
    service.get_user_byid(id).subscribe((emp)=>{
      //Assert-1
      expect(emp).toEqual(user);

    });

    //Assert -2
    const req = httpMock.expectOne('https://hivebackend-dot-hu18-groupb-java.et.r.appspot.com/api/login/jwt/id');

    //Assert-3
    expect(req.request.method).toEqual("GET");

    //Assert-4
    req.flush(user);

    //Assert -5
    httpMock.verify();

  });


  it('get_user_by_id() should call http Get method for the given route', () => {

    //Arrange
    //Set Up Data
   const user = {userName :'TheCodeBuzz', Id: '2131'}

    //Act
    service.get_user_by_id(id).subscribe((emp)=>{
      //Assert-1
      expect(emp).toEqual(user);

    });

    //Assert -2
    const req = httpMock.expectOne('https://hivebackend-dot-hu18-groupb-java.et.r.appspot.com/api/login/'+id);

    //Assert-3
    expect(req.request.method).toEqual("GET");

    //Assert-4
    req.flush(user);

    //Assert -5
    httpMock.verify();

  });


  it("should call POST API to register a new user", () => {
    const details = {userName :'TheCodeBuzz', Id: '2131'}
    service.register_user(details);

    let req = httpMock.expectOne({ method: "POST", url: 'https://hivebackend-dot-hu18-groupb-java.et.r.appspot.com/api/login' });
    expect(req.request.body).toEqual(details);
  });

  it("should call PUT API to update a new user", () => {
    const details = {userName :'TheCodeBuzz'}
    service.update_user_api(id,details);

    let req = httpMock.expectOne({ method: "PUT", url: 'https://hivebackend-dot-hu18-groupb-java.et.r.appspot.com/api/login/jwt/id' });
    expect(req.request.body).toEqual(details);
  });




  it('getleaders() should call http Get method for the given route', () => {

    //Arrange
    //Set Up Data
   const user = {userName :'TheCodeBuzz', Id: '2131'}

    //Act
    service.get_leaders("perform").subscribe((emp)=>{
      //Assert-1
      expect(emp).toEqual(user);

    });

    //Assert -2
    const req = httpMock.expectOne('https://hivebackend-dot-hu18-groupb-java.et.r.appspot.com/api/login/paging?pageNo=0&pageSize=10&sortBy=score');

    //Assert-3
    expect(req.request.method).toEqual("GET");

    //Assert-4
    req.flush(user);

    //Assert -5
    httpMock.verify();

  });


  it('get_year_wise_count() should call http Get method for the given route', () => {

    //Arrange
    //Set Up Data
   const user = {userName :'TheCodeBuzz', Id: '2131'}

    //Act
    service.get_year_wise_count().subscribe((emp)=>{
      //Assert-1
      expect(emp).toEqual(user);

    });

    //Assert -2
    const req = httpMock.expectOne('https://hivebackend-dot-hu18-groupb-java.et.r.appspot.com/activity/yearwisecount');

    //Assert-3
    expect(req.request.method).toEqual("GET");

    //Assert-4
    req.flush(user);

    //Assert -5
    httpMock.verify();

  });



  it('get_tag_wise_count() should call http Get method for the given route', () => {

    //Arrange
    //Set Up Data
   const user = {userName :'TheCodeBuzz', Id: '2131'}

    //Act
    service.get_tag_wise_count().subscribe((emp)=>{
      //Assert-1
      expect(emp).toEqual(user);

    });

    //Assert -2
    const req = httpMock.expectOne('https://hivebackend-dot-hu18-groupb-java.et.r.appspot.com/activity/tagwisecount');

    //Assert-3
    expect(req.request.method).toEqual("GET");

    //Assert-4
    req.flush(user);

    //Assert -5
    httpMock.verify();

  });

  it('forget_pass(email) should call http Get method for the given route', () => {

    //Arrange
    //Set Up Data
   const user = {userName :'TheCodeBuzz', Id: '2131'}

    //Act
    service.forget_pass("abc@gmail.com").subscribe((emp)=>{
      //Assert-1
      expect(emp).toEqual(user);

    });

    //Assert -2
    const req = httpMock.expectOne('https://hivebackend-dot-hu18-groupb-java.et.r.appspot.com/api/login/forget/abc@gmail.com');

    //Assert-3
    expect(req.request.method).toEqual("GET");

    //Assert-4
    req.flush(user);

    //Assert -5
    httpMock.verify();

  });



});
