import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmComponent } from '../confirm/confirm.component';
import { Discipline } from '../discipline';
import { DisciplineService } from '../discipline.service';
import { DisciplineComponent } from '../discipline/discipline.component';
import {ActivatedRoute, Router, NavigationStart, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-disciplines',
  templateUrl: './disciplines.component.html',
  styleUrls: ['./disciplines.component.scss']
})

export class DisciplinesComponent implements OnInit {

  constructor(private disciplineService: DisciplineService, private matDialog: MatDialog,  private activatedRoute: ActivatedRoute) {
}
  disciplines : Discipline[];

  confirm = ConfirmComponent;
  disciplineComponent = DisciplineComponent;

  ngOnInit(): void {
    this.refreshList();
  }

  refreshList()
  {
    setTimeout(
      () =>
          {
                  this.disciplineService.getDisciplines().subscribe(
                    response => {
                      this.disciplines=response;
                      },
                      error =>
                      {
                        alert(error);
                      }
                    );
         }
        );
  }

  add(disciplineId)
  {
    this.matDialog.open(this.disciplineComponent, {data: {mode:"add", disciplineId:disciplineId}, width:"800px"})
    .afterClosed().subscribe(result=> {if (result)
      this.refreshList();});
  }

}
