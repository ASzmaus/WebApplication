import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.scss']
})
export class MessageComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<MessageComponent>,  @Inject(MAT_DIALOG_DATA) public data: any) {  
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
