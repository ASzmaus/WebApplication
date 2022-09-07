import { Component, OnInit, Input, ViewChild, ElementRef } from '@angular/core';
import { FormGroup, ControlContainer, FormGroupDirective, FormControl } from '@angular/forms';

@Component({
  selector: 'app-text-input-field',
  templateUrl: './text-input-field.component.html',
  styleUrls: ['./text-input-field.component.scss']
  
})
export class TextInputFieldComponent implements OnInit {

  static nextId:number=0;
  id:number;
  constructor() { this.id= TextInputFieldComponent.nextId++; }
  @Input() form: FormGroup;
  
  @Input() inputLabel: string;
  @Input() type: string;
  @Input() formErrors: any;
  @Input() controlName: string;
  @Input() fullControlName: string;

  ngOnInit(): void {
  }

}
