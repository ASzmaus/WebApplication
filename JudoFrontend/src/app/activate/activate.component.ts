import { Component, OnInit } from '@angular/core';
import { AppService } from '../app.service';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { MessageComponent } from '../message/message.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-activate',
  templateUrl: './activate.component.html',
  styleUrls: ['./activate.component.scss']
})
export class ActivateComponent implements OnInit {

  constructor(public dialog: MatDialog, private route: ActivatedRoute,public app: AppService
    , private router: Router) { }

  private messageComponent = MessageComponent;

  ngOnInit(): void {
    this.app.activate(this.route.snapshot.paramMap.get('token'), (result) => {
      if (result.success)
      this.dialog.open(this.messageComponent, {
        data: "The account has been activated successfully. Log in to the system się do systemu."})
        .afterClosed().subscribe(()=> {this.router.navigateByUrl("/login");
        });
      else
       this.dialog.open(this.messageComponent, {
        data: "Activation error occurred: " + result.error});

    });
  }

}
