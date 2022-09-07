import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmComponent } from '../confirm/confirm.component';
import { ClubLocation } from '../location-club';
import { Location } from '../location';
import { ClubLocationService } from '../location-club.service';
import { ClubLocationComponent } from '../location-club/location-club.component';
import {ActivatedRoute, Router, NavigationStart, NavigationEnd } from '@angular/router';


@Component({
  selector: 'app-locations-club',
  templateUrl: './locations-club.component.html'
})

export class LocationsClubComponent implements OnInit {
  clubId : number;
  locationId : number;
  constructor(private clubLocationService: ClubLocationService,   private matDialog: MatDialog, private activatedRoute: ActivatedRoute) {
   this.clubId = Number(this.activatedRoute.snapshot.paramMap.get('clubId'));
   }
  locationsClub : ClubLocation[];
  confirm = ConfirmComponent;
  clubLocationComponent = ClubLocationComponent;

  ngOnInit(): void {
    this.refreshListLocationsClub();
  }

  refreshListLocationsClub()
  {
    setTimeout(
      () =>
          {
                  this.clubLocationService.getLocationsClub(this.clubId).subscribe(
                    response => {
                      this.locationsClub=response;
                      },
                      error =>
                      {
                        alert(error);
                      }
                    );
         }
        );
  }

  delete(clubId, locationId)
  {
     this.clubId = Number(this.activatedRoute.snapshot.paramMap.get('clubId'));
    this.matDialog.open(this.confirm, {data: "Are you sure you want to remove this location from the club?"})
    .afterClosed().subscribe(result=> {if (result)
      this.clubLocationService.deleteClubLocation(this.clubId,locationId).subscribe(response=>{this.refreshListLocationsClub();});});
  }

  add()
  {
    this.matDialog.open(this.clubLocationComponent, {data: {mode:"add",clubId:this.clubId, locationId:this.locationId}, width:"800px"})
    .afterClosed().subscribe(result=> {if (result)
      this.refreshListLocationsClub();});
  }
}
