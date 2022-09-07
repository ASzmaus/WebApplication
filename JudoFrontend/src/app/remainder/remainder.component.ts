import { Component, OnInit } from '@angular/core';
import { MessageComponent } from '../message/message.component';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { AppService } from '../app.service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { ValidationHelperService } from '../validation-helper.service';

@Component({
  selector: 'app-remainder',
  templateUrl: './remainder.component.html',
  styleUrls: ['./remainder.component.scss']
})
export class RemainderComponent implements OnInit {


  remainderForm: FormGroup;

  constructor(public dialog: MatDialog
    ,private formBuilder: FormBuilder
    ,public app: AppService
    , private http: HttpClient
    , private router: Router) {
  }
  ngOnInit() : void {
   this.remainderForm = this.formBuilder.group({
      email: ['',[Validators.required, Validators.email]]
});


  this.remainderForm.valueChanges.subscribe((value) => {
    this.onControlValueChanged();
  });
  this.remainderForm.statusChanges.subscribe((value) => {
    this.onControlValueChanged();
  });
}

  formErrors = {
    email: new Array(),
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
  email: {
    required: 'Email is required',
    email:"Email is in incorrect format"
  }
}

private messageComponent = MessageComponent;


remainder() {
  if (this.remainderForm.valid)
    this.app.remainder(this.remainderForm.value, (result) => {
      if (result.success)
      this.dialog.open(this.messageComponent, {
        data: "A link has been sent to the e-mail address provided to regain access to the account."})
        .afterClosed().subscribe(()=> {this.router.navigateByUrl("/home");
        });
      else
      {
        if (result.error.status==401)
        this.dialog.open(this.messageComponent, {
          data: "Invalid email address"});

          else
       this.dialog.open(this.messageComponent, {
        data: "An error has occurred: "+result.error});
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
