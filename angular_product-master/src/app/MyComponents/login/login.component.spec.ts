import { ComponentFixture, inject, TestBed } from '@angular/core/testing';
import {HttpClientModule} from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { UserDataService } from 'src/app/userdata.service';
import { LoginComponent } from './login.component';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import { Router } from '@angular/router';


let router = {
  navigate: jasmine.createSpy('navigate')
}

let username:"abc@gmail.com";
let pass:"pqr";
let stubData = {
  'username': 'testing'
};



class FakeUserDataService {

 get_user_api(username,pass){

    return Observable.of(stubData)
  }
}


describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  let newFakeUserDataService = new FakeUserDataService();
  beforeEach(async () => {
    await TestBed.configureTestingModule({

      imports: [HttpClientModule],
      declarations: [ LoginComponent ],
      providers: [

       {provide: UserDataService,useValue:newFakeUserDataService},
       { provide: Router, useValue: router }
      ]
    }).compileComponents();


  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });


  it('should have a form with 2 control', () => {
    expect(component.loginform.contains('Email')).toBeTruthy();
    expect(component.loginform.contains('Password')).toBeTruthy();
  });

  it('on filling required values form is valid', () => {
    expect(component.loginform.valid).toBeFalsy();
    component.loginform.controls['Email'].setValue("abc@gmail.com");
    component.loginform.controls['Password'].setValue("pqr");
    expect(component.loginform.valid).toBeTruthy();


  });


  it('should have Email control with required validator', () => {
    const control = component.loginform.get('Email');
    control.setValue('');
    expect(control.valid).toBeFalsy();
    control.setValue('abc');
    expect(control.valid).toBeFalsy();
    control.setValue('abc@gmail.com');
    expect(control.valid).toBeTruthy();
  });

  it('should have Password control with required validator', () => {
    const control = component.loginform.get('Password');
    control.setValue('');
    expect(control.valid).toBeFalsy();
    control.setValue('pass');
    expect(control.valid).toBeTruthy();

  });


  it('should able to Navigate to main screen on Submit Button', () => {
    component.loginform.controls['Email'].setValue("abc@gmail.com");
    component.loginform.controls['Password'].setValue("pqr");
    component.OnSubmit();

    expect(router.navigate).toHaveBeenCalledWith(['mainscreen']);

  })

  it('should able to Submit', () => {
    component.loginform.controls['Email'].setValue("abc@gmail.com");
    component.loginform.controls['Password'].setValue("pqr");
    component.OnSubmit();
    expect(component.details).toEqual(stubData);

    expect(component.OnSubmit).toBeTruthy();
  })


});
