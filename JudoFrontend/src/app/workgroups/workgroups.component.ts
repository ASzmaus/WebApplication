import { Component, OnInit, AfterViewInit  } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmComponent } from '../confirm/confirm.component';
import { WorkGroup } from '../workGroup';
import { WorkGroupService } from '../workGroup.service';
import { ClubService } from '../club.service';
import { WorkGroupComponent } from '../workGroup/workGroup.component';
import { ClubComponent } from '../club/club.component';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router, NavigationStart, NavigationEnd } from '@angular/router';
import { AppService } from '../app.service';

@Component({
  selector: 'app-workGroups',
  templateUrl: './workGroups.component.html',
  styleUrls: ['./workGroups.component.scss']
})
export class WorkGroupsComponent implements AfterViewInit {

   workGroups : WorkGroup[];
   clubId: number;
   workGroupId: number;
   confirm = ConfirmComponent;
   workGroupComponent = WorkGroupComponent;
   disciplineId : number;

   constructor(public  app: AppService, private router: Router,private activatedRoute: ActivatedRoute, private workGroupService : WorkGroupService, private matDialog: MatDialog) {
   }

  ngAfterViewInit(): void {
    this.clubId = Number(this.activatedRoute.snapshot.paramMap.get('clubId'));
    this.refreshList();

  }

  refreshList()
  {
  setTimeout(() =>{
  this.activatedRoute.params.subscribe(params => {
          this.clubId = params['clubId'];
               setTimeout(() =>{
                           this.workGroupService.getWorkGroupsByClub(this.clubId).subscribe(
                                                response => {
                                                  this.workGroups=response;
                                                  },
                                                  error =>
                                                  {
                                                    alert(error);
                                                  }
                                                );
         });
         });
       });
   }

  add()
  {
     this.matDialog.open(this.workGroupComponent, {data: {mode:"add", clubId:this.clubId, disciplineId:this.disciplineId}, width:"800px"})
     .afterClosed().subscribe(result=> {if (result)
     this.refreshList();});
  }


   get(clubId, workGroupId)
   {
    this.matDialog.open(this.workGroupComponent, {data: {mode:"get",clubId:clubId,workGroupId:workGroupId}, width:"800px"})
    .afterClosed().subscribe(result=> {if (result)
       this.refreshList();});
   }

   edit(clubId, workGroupId)
   {
         this.matDialog.open(this.workGroupComponent, {data: {mode:"edit", clubId:clubId, disciplineId:this.disciplineId, workGroupId:workGroupId}, width:"800px"})
      .afterClosed().subscribe(result=> {if (result)
      this.refreshList();});
   }

}
