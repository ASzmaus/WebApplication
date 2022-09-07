import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-registration-success',
  templateUrl: './registration-success.component.html',
  styleUrls: ['./registration-success.component.scss']
})
export class RegistrationSuccessComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<RegistrationSuccessComponent>) { }

  ngOnInit(): void {
  }

  close()
  {
    this.dialogRef.close(true);  
  }

}
