import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmComponent } from '../confirm/confirm.component';
import { Club } from '../club';
import { ClubService } from '../club.service';
import { ClubComponent } from '../club/club.component';
import {ActivatedRoute, Router, NavigationStart, NavigationEnd } from '@angular/router';
import { AppService } from '../app.service';

@Component({
  selector: 'app-clubs',
  templateUrl: './clubs.component.html',
  styleUrls: ['./clubs.component.scss']
})
export class ClubsComponent implements OnInit {

  clubs : Club[];
  clubId : number;
  club : Club;
  confirm = ConfirmComponent;
  clubComponent = ClubComponent;

  constructor(public  app: AppService, private clubService: ClubService, private route: ActivatedRoute, private router: Router,  private matDialog: MatDialog ) {
  }

  ngOnInit(): void {
    this.refreshList();
  }

  refreshList()
  {
    setTimeout(
      () =>
          {
                  this.clubService.getClubs().subscribe(
                    response => {
                      this.clubs=response;
                      },
                      error =>
                      {
                        alert(error);
                      }
                    );
         }
        );
  }

  delete(clubId)
  {
    this.matDialog.open(this.confirm, {data: "Czy na pewno chcesz usunąć ten club?"})
    .afterClosed().subscribe(result=> {if (result)
      this.clubService.deleteClub(clubId).subscribe(response=>{this.refreshList();});});
  }

  add()
  {
    this.matDialog.open(this.clubComponent, {data: {mode:"add"}, width:"800px"})
    .afterClosed().subscribe(result=> {if (result)
      this.refreshList();});
  }

  edit(clubId)
  {
    this.matDialog.open(this.clubComponent, {data: {mode:"edit",clubId:clubId}, width:"800px"})
    .afterClosed().subscribe(result=> {if (result)
      this.refreshList();});
  }

  get(clubId)
    {
     this.matDialog.open(this.clubComponent, {data: {mode:"get",clubId:clubId}, width:"800px"})
        .afterClosed().subscribe(result=> {if (result)
          this.refreshList();});

    }
}
