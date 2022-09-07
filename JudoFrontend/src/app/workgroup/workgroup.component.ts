import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { WorkGroup} from '../workGroup'
import { Discipline} from '../discipline'
import { Location} from '../location'
import { ClubLocation} from '../location-club'
import { DisciplineClub} from '../discipline-club'
import { WorkGroupService} from '../workGroup.service'
import { DisciplineClubService} from '../discipline-club.service'
import { ClubLocationService} from '../location-club.service'
import { UniqueEmailValidator} from '../unique-email-validator';
import { ValidationHelperService } from '../validation-helper.service';
import { ActivatedRoute, Router, NavigationStart, NavigationEnd } from '@angular/router';
import { AppService } from '../app.service';

@Component({
  selector: 'app-workGroup',
  templateUrl: './workGroup.component.html',
  styleUrls: ['./workGroup.component.scss']
})
export class WorkGroupComponent implements OnInit {

  workGroupForm:FormGroup;
  workGroup:WorkGroup;
  clubId:number;
  disciplineId:number;
  workGroupId:number;
  locationDto: Location;
  disciplinesClub: DisciplineClub[];
  locationsClub : ClubLocation[];

  constructor(public  app: AppService, public dialogRef: MatDialogRef<WorkGroupComponent>,@Inject(MAT_DIALOG_DATA) data: any, public workGroupService: WorkGroupService,
  public fb:FormBuilder,public uniqueEmailValidator: UniqueEmailValidator, public router: Router, private activatedRoute: ActivatedRoute, private disciplineClubService: DisciplineClubService, private clubLocationService: ClubLocationService
  ) {
    this.mode = data.mode;
    this.clubId = data.clubId;
    this.disciplineId = data.disciplineId;
    this.locationDto= data.locationDto;
    this.workGroupId = data.workGroupId;
  }

  cancel()
  {
    this.dialogRef.close(false);
  }

  validate():boolean {
    if (this.workGroupForm.valid) {
      return true;
    } else {
      Object.keys(this.workGroupForm.controls).forEach(field => {
    const control = this.workGroupForm.get(field);
    control.markAsTouched({ onlySelf: true });
  });
  for (let field in this.formErrors) {
    var control = this.workGroupForm.get(field);
    control.markAsTouched({ onlySelf: true });
  }
  this.onControlValueChanged();
  return false;
    }
  }

  onControlValueChanged() {
    const form = this.workGroupForm;

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
   'limitOfPlaces': new Array(),
   'monthlyCost': new Array(),
   'startingDate': new Array(),
   'endDate': new Array(),
   'discipline': new Array(),
   'locationDto': new Array(),
   'bankAccountNumber': new Array()
  }
  private validationMessages = {
    'name': {required: 'Name is required'},
    'limitOfPlaces': {required: 'Limit of places is required'},
    'monthlyCost': {required: 'Monthly cost is required'},
    'startingDate': {required: 'Limit of places is required'},
    'discipline': {required:"Discipline is required"},
    'locationDto': {required:"Location  is required"},
    'bankAccountNumber': {required:"Bank account number is required"}
   }

  mode:string;

  ngOnInit(): void {
    this.mode = this.mode;
    this.disciplineClubService.getDisciplinesClub(this.clubId).subscribe(
                      response => {
                                      this.disciplinesClub=response;
                                   },
                                      error =>
                                  {
                                      alert(error);
                                  }
                      );

    this.clubLocationService.getLocationsClub(this.clubId).subscribe(
                       response => {
                         this.locationsClub=response;
                         console.log(this.locationsClub);
                         },
                         error =>
                         {
                           alert(error);
                         }
                       );

    this.workGroupForm = this.fb.group({
      name: ['',[Validators.required]],
      limitOfPlaces:  ['',[Validators.required]],
      monthlyCost:  ['',[Validators.required]],
      startingDate:  ['',[Validators.required]],
      endDate:  [''],
      discipline: ['',[Validators.required]],
      locationDto:  ['',[Validators.required]],
      bankAccountNumber: ['',[Validators.required]] //, [Validators.pattern('^PL[0-9]{2}(\\s[0-9]{4}){6})$')]]
      });

    this.workGroupForm.controls['discipline'].valueChanges.subscribe((value) => {
       this.disciplineId = value.disciplineId;
       console.log("disciplineId", this.disciplineId);
    });

    this.workGroupForm.valueChanges.subscribe((value) => {
      this.onControlValueChanged();
    });
    this.workGroupForm.statusChanges.subscribe((value) => {
      this.onControlValueChanged();
    });

    if (this.mode=="edit" )
    {
      setTimeout(() =>{
        this.workGroupService.getWorkGroup(this.clubId, this.workGroupId).subscribe(
          response => {
            this.workGroup=response;
            this.workGroupForm.patchValue(this.workGroup);
          }
          );
        });
       }

  }

  save()
  {
    if (this.validate())
  {
      if (this.mode=="add" && this.app.claims['ROLE_a'])
      {
            this.workGroupService.addGroupToClubToDiscipline(this.workGroupForm.value, this.clubId, this.disciplineId ).subscribe(
              response => {
                this.dialogRef.close(true);
              },
                        error =>
                        {
                        alert(error);
                        }
                      );
                      }
      else
      if (this.mode=="edit" )
      {
      console.log("disciplineId", this.disciplineId);
      console.log("locationDto", this.locationDto);
      console.log("ReactiveFormValue",this.workGroupForm.value);
            setTimeout(() =>{
                this.workGroupService.saveGroup(this.workGroupForm.value, this.clubId, this.disciplineId, this.workGroupId).subscribe(
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
