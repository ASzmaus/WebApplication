import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PageService } from '../page.service';
import { Page } from '../page';

@Component({
  selector: 'app-page-backoffice',
  templateUrl: './page-backoffice.component.html',
  styleUrls: ['./page-backoffice.component.scss']
})
export class PageBackofficeComponent implements OnInit {

  pageForm:FormGroup;

  constructor(public dialogRef: MatDialogRef<PageBackofficeComponent>,@Inject(MAT_DIALOG_DATA) data: any, public pageService:PageService,
  public fb:FormBuilder) {
    this.mode = data.mode;
    this.id = data.id;

  }
  page:Page;
  id:number;

  cancel()
  {
    this.dialogRef.close(false);
  }

  validate():boolean {
    if (this.pageForm.valid) {
      return true;
    } else {
      Object.keys(this.pageForm.controls).forEach(field => {
    const control = this.pageForm.get(field);
    control.markAsTouched({ onlySelf: true });
  });
  for (let field in this.formErrors) {
    var control = this.pageForm.get(field);
    control.markAsTouched({ onlySelf: true });
  }
  this.onControlValueChanged();
  return false;
    }
  }

  onControlValueChanged() {
    const form = this.pageForm;

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
    'innerName': new Array(),
    'title': new Array(),
    'content': new Array(),
    'sequence': new Array()
  }
  private validationMessages = {
    'innerName': {required:"Nazwa jest wymagana"},
    'title': {required:"Tytuł jest wymagany"},
    'content': {required:"Treść jest wymagana"},
    'sequence': {required:"Kolejność jest wymagana",
    pattern: "Kolejność ma nieprawidłowy format"}

  }
  mode:string;

  ngOnInit(): void {
    this.mode = this.mode;
    this.pageForm = this.fb.group({
      innerName:['',[Validators.required]],
      title:['',[Validators.required]],
      display:[''],
      content:['',[Validators.required]],
      sequence:['',[Validators.required, Validators.pattern("\\d+")]],
    });

    this.pageForm.valueChanges.subscribe((value) => {
      this.onControlValueChanged();
    });
    this.pageForm.statusChanges.subscribe((value) => {
      this.onControlValueChanged();
    });
    if (this.mode=="edit")
    {
      this.id = this.id;
      setTimeout(()=>{
        this.pageService.getPageBackoffice(this.id).subscribe(response=> {
          this.page = response;
          this.pageForm.patchValue(this.page);
        });
      });
    }
  }

  save()
  {
    if (this.validate())
  {
      if (this.mode=="add")
      this.pageService.addPage(this.pageForm.value).subscribe(
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
        this.pageService.savePage(this.pageForm.value, this.id).subscribe(
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
