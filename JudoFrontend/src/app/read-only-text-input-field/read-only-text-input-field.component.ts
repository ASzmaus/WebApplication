import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-read-only-text-input-field',
  templateUrl: './read-only-text-input-field.component.html',
  styleUrls: ['./read-only-text-input-field.component.scss']
})
export class ReadOnlyTextInputFieldComponent implements OnInit {

  static nextId:number=0;
  id:number;
  constructor() { this.id= ReadOnlyTextInputFieldComponent.nextId++; }
  @Input()
  text: String;
  @Input()
  inputLabel: String;

  ngOnInit(): void {
  }

}
