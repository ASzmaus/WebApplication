import { Component, Inject, OnInit } from '@angular/core';
import { AppService } from '../app.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import {Club} from '../club'
import {ClubService} from '../club.service'
import {ActivatedRoute, Router, NavigationStart, NavigationEnd } from '@angular/router';
@Component({
  selector: 'app-club',
  templateUrl: './club.component.html',
  styleUrls: ['./club.component.scss']
})
export class ClubComponent implements OnInit {
clubForm:FormGroup;

  constructor(public  app: AppService,public dialogRef: MatDialogRef<ClubComponent>,@Inject(MAT_DIALOG_DATA) data: any, public clubService:ClubService,
  public fb:FormBuilder,private route: ActivatedRoute, private router: Router ) {
    this.mode = data.mode;
    this.clubId = data.clubId;

  }
  club: Club;
  clubId: number;

  cancel()
  {
    this.dialogRef.close(false);
  }

  validate():boolean {
    if (this.clubForm.valid) {
      return true;
    } else {
      Object.keys(this.clubForm.controls).forEach(field => {
    const control = this.clubForm.get(field);
    control.markAsTouched({ onlySelf: true });
  });
  for (let field in this.formErrors) {
    var control = this.clubForm.get(field);
    control.markAsTouched({ onlySelf: true });
  }
  this.onControlValueChanged();
  return false;
    }
  }

  onControlValueChanged() {
    const form = this.clubForm;

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
    'name': new Array(),
    'domain': new Array(),
    'phoneNumber': new Array(),
    'addressEmail': new Array()
  }
  private validationMessages = {
    'name': {required:"Nazwa jest wymagana"},
    'domain': {required:"Domena jest wymagana"},
    'phoneNumber': {required:"Numer telefonu jest wymagany"},
    'addressEmail': {required:"Adres e-mail jest wymagany",
    email: 'E-mail address must be provided'}

  }
  mode:string;

  ngOnInit(): void {
    this.mode = this.mode;
    this.clubForm = this.fb.group({
      name:['',[Validators.required]],
      domain:['',[Validators.required]],
      phoneNumber:['',[Validators.required]],
      addressEmail:['',[Validators.required, Validators.email]],
    });

    this.clubForm.valueChanges.subscribe((value) => {
      this.onControlValueChanged();
    });
    this.clubForm.statusChanges.subscribe((value) => {
      this.onControlValueChanged();
    });

    if (this.mode=="edit")
    {
      this.clubId = this.clubId;
      setTimeout(()=>{
        this.clubService.getClub(this.clubId).subscribe(response=> {
          this.club = response;
          this.clubForm.patchValue(this.club);
        });
    });
    }

    else if (this.mode=="get")
          {
                   this.clubId = this.clubId;
                   setTimeout(()=>{
                   this.clubService.getClub(this.clubId).subscribe(response=> {
                   this.club = response;
                   this.clubForm.patchValue(this.club);

                 });
             });
         }
     }

  save()
  {
    if (this.validate())
  {
      if (this.mode=="add")
      {
      this.clubService.addClub(this.clubForm.value).subscribe(
        response => {
          this.dialogRef.close(true);
        },
                  error =>
                  {
                  alert(error);
                  }
                );
                }
      else if (this.mode=="edit")
      {
        this.clubService.saveClub(this.clubForm.value, this.clubId).subscribe(
          response => {
            this.dialogRef.close(true);
    },
                    error =>
                    {
                    alert(error);
                    }
                  );
      }
    }
  }
}
