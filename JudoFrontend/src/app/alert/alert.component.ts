import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.scss']
})
export class AlertComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<AlertComponent>,  @Inject(MAT_DIALOG_DATA) public data: any) {  
    this.message = data;
  }

message:string;

  ngOnInit(): void {
  }

  ok()
  {
    this.dialogRef.close(true);
  }
}
