import { Component, OnInit } from '@angular/core';
import { AppService } from '../app.service';
import { ValidationHelperService } from '../validation-helper.service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import {UniqueEmailValidator} from '../unique-email-validator';
import {MatDialogRef,MAT_DIALOG_DATA} from '@angular/material/dialog';
import { RegistrationSuccessComponent } from '../registration-success/registration-success.component';

import {TextInputFieldComponent} from '../text-input-field/text-input-field.component'
import { MatDialog } from '@angular/material/dialog';
import {
  FormGroup,
  FormBuilder,
  Validators,
  ReactiveFormsModule,FormControl, ValidationErrors, AbstractControl
} from '@angular/forms';
import { MessageComponent } from '../message/message.component';
@Component({
  templateUrl: './register.component.html'
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;

  constructor(public dialog: MatDialog
    ,private formBuilder: FormBuilder
    ,public uniqueEmailValidator: UniqueEmailValidator
    ,public app: AppService
    , private http: HttpClient
    , private router: Router) {
  }
  ngOnInit() : void {
   this.registerForm = this.formBuilder.group({
      email: ['',[Validators.required, Validators.email],[ this.uniqueEmailValidator.existingEmailValidator()]],
      password: ['',[Validators.required,Validators.minLength(8)]],
      confirmPassword: ['',[Validators.required,
        ValidationHelperService.matchValues('password')]]});


        this.registerForm.valueChanges.subscribe((value) => {
    this.onControlValueChanged();
  });
  this.registerForm.statusChanges.subscribe((value) => {
    this.onControlValueChanged();
  });
 this.onControlValueChanged();
 this.registerForm .controls.password.valueChanges.subscribe(() => {
  this.registerForm .controls.confirmPassword.updateValueAndValidity();
});
  }

  formErrors = {
    email: new Array(),
    password: new Array(),
  confirmPassword: new Array()
}

onControlValueChanged() {
  const form = this.registerForm;

  for (let field in this.formErrors) {
    var array = new Array();
    let control = form.get(field);

    if (control && (control.dirty || control.touched) && !control.valid) {
      const validationMessages = this.validationMessages[field];
      for (const key in control.errors) {
        array.push(validationMessages[key]);
      }
    }
    this.formErrors[field] = array;

  }
}

private validationMessages = {
  email: {
    required: 'Login is required',
    email: 'E-mail address must be provided',
    loginUnique: "Login is already in use, please enter another"
  },
  password: {
    required: 'Password is required',
    minlength: 'Password should be at least 8 characters long'
  },
  confirmPassword: {
    required: 'Password repetition is required',
    isMatching: 'Values do not match'
  }
}

private messageComponent = MessageComponent;


register() {
  if (this.registerForm.valid)
    this.app.register(this.registerForm.value, (result) => {
      if (result.success)
      this.dialog.open(this.messageComponent, {
        data: "An activation link has been sent to the e-mail address provided. Click on it to activate your account and then log in to the system."})
        .afterClosed().subscribe(()=> {this.router.navigateByUrl("/home");
        });
      else
       this.dialog.open(this.messageComponent, {
        data: "A registration error has occurred: "+result.error});

    });
    return false;
  }
  onSubmit() {
  if (this.registerForm.valid) {
    ;
  } else {
    Object.keys(this.registerForm.controls).forEach(field => {
  const control = this.registerForm.get(field);
  control.markAsTouched({ onlySelf: true });
});
this.onControlValueChanged();
  }
}

}
