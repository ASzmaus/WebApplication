import { Component, Input, Inject, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import {Location} from '../location'
import {LocationService} from '../location.service'
@Component({
  selector: 'app-location',
  templateUrl: './location.component.html'
})
export class LocationComponent implements OnInit {

 locationForm:FormGroup;

  constructor(public dialogRef: MatDialogRef<LocationComponent>, @Inject(MAT_DIALOG_DATA) data: any, public locationService:LocationService,
  public fb:FormBuilder) {
    this.mode = data.mode;
    this.locationId = data.locationId;
  }
  location:Location;
  locationId:number;

  cancel()
  {
    this.dialogRef.close(false);
  }

  validate():boolean {
    if (this.locationForm.valid) {
      return true;
    } else {
      Object.keys(this.locationForm.controls).forEach(field => {
    const control = this.locationForm.get(field);
    control.markAsTouched({ onlySelf: true });
  });
  for (let field in this.formErrors) {
    var control = this.locationForm.get(field);
    control.markAsTouched({ onlySelf: true });
  }
  this.onControlValueChanged();
  return false;
    }
  }

  onControlValueChanged() {
    const form = this.locationForm;

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
  'street': new Array(),
  'houseNumber':new Array(),
  'city': new Array(),
  'postcode': new Array(),
  'description': new Array()
 }
  private validationMessages = {
   'street': {required:"Street is reuired"},
   'houseNumber': {required:"Description is reuired"},
   'city': {required:"City is reuired"},
   'postcode': {required:"Postcode is reuired"},
   'description': {required:"Description is reuired"}
  }
  mode:string;

  ngOnInit(): void {
    this.mode = this.mode;
    this.locationForm = this.fb.group({
      street:['',[Validators.required]],
      houseNumber:['',[Validators.required]],
      city:['',[Validators.required]],
      postcode:['',[Validators.required]],
      description:['',[Validators.required]]
    });

    this.locationForm.valueChanges.subscribe((value) => {
      this.onControlValueChanged();
    });
    this.locationForm.statusChanges.subscribe((value) => {
      this.onControlValueChanged();
    });
    if (this.mode=="edit" )
    {
      setTimeout(()=>{
        this.locationService.getLocation(this.locationId).subscribe(response=> {
          this.location = response;
          this.locationForm.patchValue(this.location);
        });
      });
    }
  }

 save()
  {
    if (this.validate())
  {
      if (this.mode=="add")
      this.locationService.addLocation(this.locationForm.value).subscribe(
        response => {
          this.dialogRef.close(true);
  },
                  error =>
                  {
                  alert(error);
                  }
                );
      else if (this.mode=="edit")
      {
        this.locationService.saveLocation(this.locationForm.value, this.locationId).subscribe(
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
