import { Component, Input, Inject, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import {Discipline} from '../discipline'
import {DisciplineService} from '../discipline.service'
@Component({
  selector: 'app-discipline',
  templateUrl: './discipline.component.html',
  styleUrls: ['./discipline.component.scss']
})
export class DisciplineComponent implements OnInit {

 disciplineForm:FormGroup;

  constructor(public dialogRef: MatDialogRef<DisciplineComponent>,@Inject(MAT_DIALOG_DATA) data: any, public disciplineService:DisciplineService,
  public fb:FormBuilder) {
    this.mode = data.mode;
    this.id = data.id;
  }
  discipline:Discipline;
  id:number;

  cancel()
  {
    this.dialogRef.close(false);
  }

  validate():boolean {
    if (this.disciplineForm.valid) {
      return true;
    } else {
      Object.keys(this.disciplineForm.controls).forEach(field => {
    const control = this.disciplineForm.get(field);
    control.markAsTouched({ onlySelf: true });
  });
  for (let field in this.formErrors) {
    var control = this.disciplineForm.get(field);
    control.markAsTouched({ onlySelf: true });
  }
  this.onControlValueChanged();
  return false;
    }
  }

  onControlValueChanged() {
    const form = this.disciplineForm;

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
    'description': new Array()
 }
  private validationMessages = {
    'name': {required:"Nazwa jest wymagana"},
    'description': {required:"Description jest wymagana"}
  }
  mode:string;

  ngOnInit(): void {
    this.mode = this.mode;
    this.disciplineForm = this.fb.group({
      name:['',[Validators.required]],
      description:['',[Validators.required]]
    });

    this.disciplineForm.valueChanges.subscribe((value) => {
      this.onControlValueChanged();
    });
    this.disciplineForm.statusChanges.subscribe((value) => {
      this.onControlValueChanged();
    });
  }
}
