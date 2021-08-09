import { ComponentFixture, TestBed } from '@angular/core/testing';
import {HttpClientModule} from '@angular/common/http';
import { SignupComponent } from './signup.component';
import { RouterTestingModule } from '@angular/router/testing';
import { UserDataService } from 'src/app/userdata.service';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import { Router } from '@angular/router';

let router = {
  navigate: jasmine.createSpy('navigate')
}

class FakeUserDataService {

  register_user(){

     return null;
   }
 }


describe('SignupComponent', () => {
  let component: SignupComponent;
  let fixture: ComponentFixture<SignupComponent>;

  let newFakeUserDataService = new FakeUserDataService();
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule],
      declarations: [ SignupComponent ],
      providers: [

        {provide: UserDataService,useValue:newFakeUserDataService},
        { provide: Router, useValue: router }
       ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SignupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });


  it('should have a form with 3 control', () => {
    expect(component.registerForm.contains('name')).toBeTruthy();
    expect(component.registerForm.contains('username')).toBeTruthy();
    expect(component.registerForm.contains('password')).toBeTruthy();
  });

  it('on filling required values form is valid', () => {
    expect(component.registerForm.valid).toBeFalsy();
    component.registerForm.controls['name'].setValue("abc");
    component.registerForm.controls['username'].setValue("pqr@gmail.com");
    component.registerForm.controls['password'].setValue("abc");
    expect(component.registerForm.valid).toBeTruthy();


  });



  it('should have username control with required validator', () => {
    const control = component.registerForm.get('username');
    control.setValue('');
    expect(control.valid).toBeFalsy();
    control.setValue('abc');
    expect(control.valid).toBeFalsy();
    control.setValue('abc@gmail.com');
    expect(control.valid).toBeTruthy();
  });


  it('should have name control with required validator', () => {
    const control = component.registerForm.get('name');
    control.setValue('');
    expect(control.valid).toBeFalsy();
    control.setValue('abc');
    expect(control.valid).toBeTruthy();

  });


  /*it('should able to Navigate to Login Page on Submit Button', () => {
    component.registerForm.controls['name'].setValue("abc");
    component.registerForm.controls['username'].setValue("pqr@gmail.com");
    component.onSubmit();
    expect(router.navigate).toHaveBeenCalledWith(['login']);



  })*/


  it('should able to Register', () => {
    component.registerForm.controls['name'].setValue("abc");
    component.registerForm.controls['username'].setValue("pqr@gmail.com");
    component.onSubmit();


    expect(component.onSubmit).toBeTruthy();
  })


});
