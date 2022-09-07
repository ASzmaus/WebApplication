import { Component, OnInit, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-textarea-field',
  templateUrl: './textarea-field.component.html',
  styleUrls: ['./textarea-field.component.scss']
})
export class TextareaFieldComponent implements OnInit {

  static nextId:number=0;
  id:number;
  constructor() { this.id= TextareaFieldComponent.nextId++; }
   @Input() form: FormGroup;
  
  @Input() inputLabel: string;
  @Input() formErrors: any;
  @Input() controlName: string;
  @Input() fullControlName: string;

  ngOnInit(): void {
  }

}
