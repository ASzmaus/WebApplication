import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmComponent } from '../confirm/confirm.component';
import { DisciplineClub } from '../discipline-club';
import { Discipline } from '../discipline';
import { DisciplineClubService } from '../discipline-club.service';
import { DisciplineClubComponent } from '../discipline-club/discipline-club.component';
import {ActivatedRoute, Router, NavigationStart, NavigationEnd } from '@angular/router';


@Component({
  selector: 'app-disciplines-club',
  templateUrl: './disciplines-club.component.html',
  styleUrls: ['./disciplines-club.component.scss']
})

export class DisciplinesClubComponent implements OnInit {
  clubId : number;
  disciplineId : number;
  constructor(private disciplineClubService: DisciplineClubService,   private matDialog: MatDialog, private activatedRoute: ActivatedRoute) {
   this.clubId = Number(this.activatedRoute.snapshot.paramMap.get('clubId'));
  }

  disciplinesClub : DisciplineClub[];

  confirm = ConfirmComponent;
  disciplineClubComponent = DisciplineClubComponent;

  ngOnInit(): void {
    this.refreshListDisciplinesClub();
  }

  refreshListDisciplinesClub()
  {
    setTimeout(
      () =>
          {
                  this.disciplineClubService.getDisciplinesClub(this.clubId).subscribe(
                    response => {
                      this.disciplinesClub=response;
                      },
                      error =>
                      {
                        alert(error);
                      }
                    );
         }
        );
  }

  delete(clubId, disciplineId)
  {
     this.clubId = Number(this.activatedRoute.snapshot.paramMap.get('clubId'));
    this.matDialog.open(this.confirm, {data: "Are you sure you want to remove this discipline from the club?"})
    .afterClosed().subscribe(result=> {if (result)
      this.disciplineClubService.deleteDisciplineClub(this.clubId,disciplineId).subscribe(response=>{this.refreshListDisciplinesClub();});});
  }

  add()
  {
    this.matDialog.open(this.disciplineClubComponent, {data: {mode:"add",clubId:this.clubId, disciplineId:this.disciplineId}, width:"800px"})
    .afterClosed().subscribe(result=> {if (result)
      this.refreshListDisciplinesClub();});
  }
}
