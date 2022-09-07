import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-confirm',
  templateUrl: './confirm.component.html',
  styleUrls: ['./confirm.component.scss']
})
export class ConfirmComponent implements OnInit {


  constructor(public dialogRef: MatDialogRef<ConfirmComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {
      this.message = data;
     }

message:string;

  ngOnInit(): void {
  }

  no()
  {
    this.dialogRef.close(false);
  }

  yes()
  {
    this.dialogRef.close(true);
  }
}
