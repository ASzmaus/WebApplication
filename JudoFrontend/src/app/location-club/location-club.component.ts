import { Component, Injectable, Inject, Input, OnInit, AfterViewInit} from '@angular/core';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import {ClubLocation} from '../location-club';
import {Location} from '../location'
import {ClubLocationService} from '../location-club.service'
import {LocationService} from '../location.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-location-club',
  templateUrl: './location-club.component.html'
})
@Injectable()
export class ClubLocationComponent implements OnInit {
  locations: Location[];
  clubLocationForm:FormGroup;
  clubId:number;
  locationId:number;
  name: string;

  constructor(public dialogRef: MatDialogRef<ClubLocationComponent>,@Inject(MAT_DIALOG_DATA) data: any, private clubLocationService:ClubLocationService,
  public fb:FormBuilder, private locationService: LocationService, private changeDetector: ChangeDetectorRef) {
    this.mode = data.mode;
    this.clubId = data.clubId;
    this.locationId = data.locationId;
  }
  cancel()
  {
    this.dialogRef.close(false);
  }

  validate():boolean {
    if (this.clubLocationForm.valid) {
      return true;
    } else {
      Object.keys(this.clubLocationForm.controls).forEach(field => {
    const control = this.clubLocationForm.get(field);
    control.markAsTouched({ onlySelf: true });
  });
  for (let field in this.formErrors) {
    var control = this.clubLocationForm.get(field);
    control.markAsTouched({ onlySelf: true });
  }
  this.onControlValueChanged();
  return false;
    }
  }

  onControlValueChanged() {
    const form = this.clubLocationForm;

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
    'location': new Array()
 }
  private validationMessages = {
    'location': {required:"Location is required"}

  }
  mode:string;

    ngOnInit(): void {

        this.locationService.getLocations().subscribe(
                  response => {
                                 this.locations=response;
                                 console.log(this.locations);
                               },
                                  error =>
                               {
                                  alert(error);
                               }
                        );
    this.mode = this.mode;

    this.clubLocationForm = this.fb.group({
    location: ['',[Validators.required]]
    });
    this.clubLocationForm.controls['location'].valueChanges.subscribe((value) => {
         this.locationId = value.locationId;
     });

    this.clubLocationForm.valueChanges.subscribe((value) => {
      this.onControlValueChanged();
    });
    this.clubLocationForm.statusChanges.subscribe((value) => {
      this.onControlValueChanged();
    });

  }

 save()
  {
    if (this.validate())
  {
      this.clubLocationService.addClubLocation(this.clubLocationForm.value, this.clubId,  this.locationId).subscribe(
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
