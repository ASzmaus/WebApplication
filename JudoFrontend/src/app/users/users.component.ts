import { Component, OnInit, AfterViewInit  } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmComponent } from '../confirm/confirm.component';
import { User } from '../user';
import { UserService } from '../user.service';
import { ClubService } from '../club.service';
import { UserComponent } from '../user/user.component';
import { ClubComponent } from '../club/club.component';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import {ActivatedRoute, Router, NavigationStart, NavigationEnd } from '@angular/router';
import { AppService } from '../app.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements AfterViewInit {

   users : User[];
   clubId: number;
   userId: number;
   confirm = ConfirmComponent;
   userComponent = UserComponent;

   constructor(public  app: AppService, private router: Router,private activatedRoute: ActivatedRoute, private userService : UserService, private matDialog: MatDialog) {
   }

  ngAfterViewInit(): void {
    this.clubId = Number(this.activatedRoute.snapshot.paramMap.get('clubId'))
    this.refreshList();
  }

  refreshList()
  {
  setTimeout(() =>{
  this.activatedRoute.params.subscribe(params => {
          this.clubId = params['clubId'];
               setTimeout(() =>{
               if (this.app.claims['ROLE_o']){
          this.userService.getAdmins(this.clubId).subscribe(
                               response => {
                                 this.users=response;
                                 },
                                 error =>
                                 {
                                   alert(error);
                                 }
                               );
           }
           else if (this.app.claims['ROLE_a']) {
                           this.userService.getStaffs(this.clubId).subscribe(
                                                response => {
                                                  this.users=response;
                                                  },
                                                  error =>
                                                  {
                                                    alert(error);
                                                  }
                                                );
            }
         });
         });
       });
   }

  delete(userId)
  {
    this.matDialog.open(this.confirm, {data: "Are you sure you want to delete this user?"})
    .afterClosed().subscribe(result=> {
    if (result &&  this.app.claims['ROLE_o'])
      this.userService.deleteAdmin(userId).subscribe(response=>{this.refreshList();});
    if (result &&  this.app.claims['ROLE_a'])
      this.userService.deleteStaff(userId).subscribe(response=>{this.refreshList();});
      });
  }

  add()
  {
     this.clubId = Number(this.activatedRoute.snapshot.paramMap.get('clubId'));
     this.matDialog.open(this.userComponent, {data: {mode:"add", clubId:this.clubId}, width:"800px"})
     .afterClosed().subscribe(result=> {if (result)
     this.refreshList();});
  }

  edit(clubId,userId)
  {
    this.matDialog.open(this.userComponent, {data: {mode:"edit", clubId:clubId,userId:userId}, width:"800px"})
    .afterClosed().subscribe(result=> {if (result)
      this.refreshList();});
  }

   get(clubId,userId)
      {
       this.matDialog.open(this.userComponent, {data: {mode:"get",clubId:clubId,userId:userId}, width:"800px"})
          .afterClosed().subscribe(result=> {if (result)
            this.refreshList();});
      }
}
