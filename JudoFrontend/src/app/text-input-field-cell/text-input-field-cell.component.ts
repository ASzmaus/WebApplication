import { Component, OnInit, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-text-input-field-cell',
  templateUrl: './text-input-field-cell.component.html',
  styleUrls: ['./text-input-field-cell.component.scss']
})
export class TextInputFieldCellComponent implements OnInit {

  static nextId:number=0;
  id:number;
  constructor() { this.id= TextInputFieldCellComponent.nextId++; }
  @Input() form: FormGroup;
  
  @Input() formErrors: any;
  @Input() inputLabel: string;
  @Input() type: string;
  @Input() controlName: string;
  @Input() fullControlName: string;
  @Input() accessibilityLabel: string;

  ngOnInit(): void {
  }

}
