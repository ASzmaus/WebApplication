import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import {User} from '../user'
import {UserService} from '../user.service'
import {UniqueEmailValidator} from '../unique-email-validator';
import { ValidationHelperService } from '../validation-helper.service';
import {ActivatedRoute, Router, NavigationStart, NavigationEnd } from '@angular/router';
import { AppService } from '../app.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {

  userForm:FormGroup;
  user:User;
  clubId:number;
  userId:number;


  constructor(public  app: AppService, public dialogRef: MatDialogRef<UserComponent>,@Inject(MAT_DIALOG_DATA) data: any, public userService:UserService,
  public fb:FormBuilder,public uniqueEmailValidator: UniqueEmailValidator, public router: Router, private activatedRoute: ActivatedRoute
  ) {
    this.mode = data.mode;
    this.userId = data.userId;
    this.clubId = data.clubId;
  }

  cancel()
  {
    this.dialogRef.close(false);
  }

  validate():boolean {
    if (this.userForm.valid) {
      return true;
    } else {
      Object.keys(this.userForm.controls).forEach(field => {
    const control = this.userForm.get(field);
    control.markAsTouched({ onlySelf: true });
  });
  for (let field in this.formErrors) {
    var control = this.userForm.get(field);
    control.markAsTouched({ onlySelf: true });
  }
  this.onControlValueChanged();
  return false;
    }
  }

  onControlValueChanged() {
    const form = this.userForm;

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

  formErrors = {
    'email': new Array(),
    password: new Array(),
    confirmPassword: new Array()

  }
  private validationMessages = {
    'email': {required: 'Login is required', email: 'E-mail address must be provided',
    loginUnique: "Login is already in use, please enter another"},
    password: {required: 'Password is required',minlength: 'Password should be at least 8 characters long'},
    confirmPassword: {required: 'Password repetition is required',isMatching: 'Values do not match'
    }
   }

  mode:string;

  ngOnInit(): void {
    this.mode = this.mode;
    this.userId = this.userId;
    this.clubId = this.clubId;
    if (this.mode=="edit")
    this.userForm = this.fb.group({
       email: ['',[Validators.required, Validators.email]],
       password: ['',[Validators.minLength(8)]],
       confirmPassword: ['',[ValidationHelperService.matchValues('password')]]
      });
    else
    this.userForm = this.fb.group({
      email: ['',[Validators.required, Validators.email],[ this.uniqueEmailValidator.existingEmailValidator()]],
      password: ['',[Validators.required,Validators.minLength(8)]],
      confirmPassword: ['',[Validators.required,
        ValidationHelperService.matchValues('password')]]
    });

    this.userForm.valueChanges.subscribe((value) => {
      this.onControlValueChanged();
    });
    this.userForm.statusChanges.subscribe((value) => {
      this.onControlValueChanged();
    });

    if ((this.mode=="edit" || this.mode=="get") && this.app.claims['ROLE_o'])
    {
               setTimeout(() =>{
               this.userService.getAdmin(this.clubId,this.userId).subscribe(
                                   response => {
                                     this.user=response;
                                     this.userForm.patchValue(this.user);
                                     }
                                   );
           });
       }
       else if ((this.mode=="edit" || this.mode=="get") && this.app.claims['ROLE_a'] )
           {
                      setTimeout(() =>{
                      this.userService.getStaff(this.clubId,this.userId).subscribe(
                                          response => {
                                            this.user=response;
                                            this.userForm.patchValue(this.user);
                                            }
                                          );
                    });
              }
  }

  save()
  {
    if (this.validate())
  {
      if (this.mode=="add" && this.app.claims['ROLE_o'])
      {
          this.clubId = this.clubId ;
          this.userId= this.userId;
            this.userService.addAdmin(this.userForm.value, this.clubId ).subscribe(
              response => {
                this.dialogRef.close(true);
              },
                        error =>
                        {
                        alert(error);
                        }
                      );
                      }
      else if (this.mode=="edit" &&  this.app.claims['ROLE_o'])
      {
          this.clubId = this.clubId;
          this.userId = this.userId;
            setTimeout(() =>{
              this.userService.saveAdmin(this.userForm.value, this.clubId, this.userId).subscribe(
                response => {
                  this.dialogRef.close(true);
                },
                error =>
                {
                  alert(error);
                }
              );
            });
      }
      else if (this.mode=="add" && this.app.claims['ROLE_a'])
            {
                this.clubId = this.clubId ;
                this.userId= this.userId;
                  this.userService.addStaff(this.userForm.value, this.clubId ).subscribe(
                    response => {
                      this.dialogRef.close(true);
                    },
                              error =>
                              {
                              alert(error);
                              }
                            );
                            }
            else if (this.mode=="edit" &&  this.app.claims['ROLE_a'])
            {
                this.clubId = this.clubId;
                this.userId= this.userId;
                  setTimeout(() =>{
                    this.userService.saveStaff(this.userForm.value, this.clubId, this.userId).subscribe(
                      response => {
                        this.dialogRef.close(true);
                      },
                      error =>
                      {
                        alert(error);
                      }
                    );
                  });
            }
    }
  }
}
