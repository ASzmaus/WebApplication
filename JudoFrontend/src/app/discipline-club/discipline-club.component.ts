import { Component, Injectable, Inject, Input, OnInit, AfterViewInit} from '@angular/core';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import {DisciplineClub} from '../discipline-club';
import {Discipline} from '../discipline'
import {DisciplineClubService} from '../discipline-club.service'
import {DisciplineService} from '../discipline.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-discipline-club',
  templateUrl: './discipline-club.component.html',
  styleUrls: ['./discipline-club.component.scss']
})
@Injectable()
export class DisciplineClubComponent implements OnInit {
  disciplines: Discipline[];
  disciplineClubForm:FormGroup;
  clubId:number;
  disciplineId:number;
  name: string;

  constructor(public dialogRef: MatDialogRef<DisciplineClubComponent>,@Inject(MAT_DIALOG_DATA) data: any, private disciplineClubService:DisciplineClubService,
  public fb:FormBuilder, private disciplineService: DisciplineService, private changeDetector: ChangeDetectorRef) {
    this.mode = data.mode;
    this.clubId = data.clubId;
    this.disciplineId = data.disciplineId;
  }
  cancel()
  {
    this.dialogRef.close(false);
  }

  validate():boolean {
    if (this.disciplineClubForm.valid) {
      return true;
    } else {
      Object.keys(this.disciplineClubForm.controls).forEach(field => {
    const control = this.disciplineClubForm.get(field);
    control.markAsTouched({ onlySelf: true });
  });
  for (let field in this.formErrors) {
    var control = this.disciplineClubForm.get(field);
    control.markAsTouched({ onlySelf: true });
  }
  this.onControlValueChanged();
  return false;
    }
  }

  onControlValueChanged() {
    const form = this.disciplineClubForm;

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
    'discipline': new Array()
 }
  private validationMessages = {
    'discipline': {required:"Nazwa jest wymagana"}

  }
  mode:string;

    ngOnInit(): void {

      this.disciplineService.getDisciplines().subscribe(
           response => {
                this.disciplines=response;
                       },
                             error =>
                       {
                              alert(error);
                       }
                );
    this.mode = this.mode;

    this.disciplineClubForm = this.fb.group({
    discipline: ['',[Validators.required]]
    });
    this.disciplineClubForm.controls['discipline'].valueChanges.subscribe((value) => {
         this.disciplineId = value.disciplineId;
     });

    this.disciplineClubForm.valueChanges.subscribe((value) => {
      this.onControlValueChanged();
    });
    this.disciplineClubForm.statusChanges.subscribe((value) => {
      this.onControlValueChanged();
    });
  }

 save()
  {
    if (this.validate())
  {

      console.log("disciplineId", this.disciplineId);
      console.log(this.disciplineClubForm.value);
      this.disciplineClubService.addDisciplineClub(this.disciplineClubForm.value, this.clubId,  this.disciplineId).subscribe(
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
