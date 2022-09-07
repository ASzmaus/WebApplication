import { Component, OnInit } from '@angular/core';
import { AppService } from '../app.service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import {TextInputFieldComponent} from '../text-input-field/text-input-field.component'
import {
  FormGroup,
  FormBuilder,
  Validators,
  ReactiveFormsModule,FormControl
} from '@angular/forms';
@Component({
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;

  constructor(private formBuilder: FormBuilder,public app: AppService, private http: HttpClient, private router: Router) {
  }
  ngOnInit() : void {
   this.loginForm = this.formBuilder.group({
      username: ['',[Validators.required]],
      password: ['',[Validators.required]]});


        this.loginForm.valueChanges.subscribe((value) => {
    this.onControlValueChanged();
  });
 this.onControlValueChanged();

  }

  formErrors = {
  username: new Array(),
  password: new Array()
}
onControlValueChanged() {
  const form = this.loginForm;

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
  username: {
    required: 'Login is required'
  },
  password: {
    required: 'Password is required'
  }
}



  login() {
  if (this.loginForm.valid)
    this.app.authenticate(this.loginForm.value, () => {
        this.router.navigateByUrl('/home');
    });
    return false;
  }

  onSubmit() {
  if (this.loginForm.valid) {
    ;
  } else {
    Object.keys(this.loginForm.controls).forEach(field => {
  const control = this.loginForm.get(field);
  control.markAsTouched({ onlySelf: true });
});
this.onControlValueChanged();
  }
}

}
