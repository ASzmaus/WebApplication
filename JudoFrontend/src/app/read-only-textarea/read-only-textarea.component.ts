import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-read-only-textarea',
  templateUrl: './read-only-textarea.component.html',
  styleUrls: ['./read-only-textarea.component.scss']
})
export class ReadOnlyTextareaComponent implements OnInit {

  static nextId:number=0;
  id:number;
  constructor() { this.rows = 1; this.id= ReadOnlyTextareaComponent.nextId++; }

  @Input()
  rows:number;
  @Input()
  text: String;
  @Input()
  inputLabel: String;

  ngOnInit(): void {
  }

}
