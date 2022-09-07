import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { UniqueEmailValidator } from '../unique-email-validator';
import { AppService } from '../app.service';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { ValidationHelperService } from '../validation-helper.service';
import { MessageComponent } from '../message/message.component';

@Component({
  selector: 'app-remainder-enter-password',
  templateUrl: './remainder-enter-password.component.html',
  styleUrls: ['./remainder-enter-password.component.scss']
})
export class RemainderEnterPasswordComponent implements OnInit {


  remainderForm: FormGroup;
  token:string;
  constructor(public dialog: MatDialog
    ,private formBuilder: FormBuilder
    ,public uniqueEmailValidator: UniqueEmailValidator
    ,public app: AppService
    , private http: HttpClient
    , private router: Router,private route: ActivatedRoute) {
  }
  ngOnInit() : void {
    this.token = this.route.snapshot.paramMap.get('token');
   this.remainderForm = this.formBuilder.group({
      password: ['',[Validators.required,Validators.minLength(8)]],
      confirmPassword: ['',[Validators.required,
        ValidationHelperService.matchValues('password')]]});


        this.remainderForm.valueChanges.subscribe((value) => {
    this.onControlValueChanged();
  });
  this.remainderForm.statusChanges.subscribe((value) => {
    this.onControlValueChanged();
  });
 this.onControlValueChanged();
 this.remainderForm .controls.password.valueChanges.subscribe(() => {
  this.remainderForm .controls.confirmPassword.updateValueAndValidity();
});
  }

  formErrors = {
    password: new Array(),
  confirmPassword: new Array()
}

onControlValueChanged() {
  const form = this.remainderForm;

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


remainder() {
  if (this.remainderForm.valid)
    this.app.remainderEnterPassword(this.remainderForm.value, this.token, (result) => {
      if (result.success)
      this.dialog.open(this.messageComponent, {
        data: "The new password has been set successfully. Log in to the system."})
        .afterClosed().subscribe(()=> {this.router.navigateByUrl("/login");
        });
      else
      {
        if (result.error.status==401)
        this.dialog.open(this.messageComponent, {
          data: "Link do ustawienia nowego hasła utracił ważność. Wygeneruj nowy"});
          else
       this.dialog.open(this.messageComponent, {
        data: "Wystąpił błąd ustawienia nowego hasła: "+result.error});
       }
    });
    return false;
  }
  onSubmit() {
  if (this.remainderForm.valid) {
    ;
  } else {
    Object.keys(this.remainderForm.controls).forEach(field => {
  const control = this.remainderForm.get(field);
  control.markAsTouched({ onlySelf: true });
});
this.onControlValueChanged();
  }
}

}
