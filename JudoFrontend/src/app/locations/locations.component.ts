import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmComponent } from '../confirm/confirm.component';
import { Location } from '../location';
import { LocationService } from '../location.service';
import { LocationComponent } from '../location/location.component';
import {ActivatedRoute, Router, NavigationStart, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-locations',
  templateUrl: './locations.component.html'
})

export class LocationsComponent implements OnInit {

  constructor(private locationService: LocationService, private matDialog: MatDialog,  private activatedRoute: ActivatedRoute) {
}
  locations : Location[];

  confirm = ConfirmComponent;
  locationComponent = LocationComponent;

  ngOnInit(): void {
    this.refreshList();
  }

  refreshList()
  {
    setTimeout(
      () =>
          {
                  this.locationService.getLocations().subscribe(
                    response => {
                      this.locations=response;
                      },
                      error =>
                      {
                        alert(error);
                      }
                    );
         }
        );
  }

  add()
  {
    this.matDialog.open(this.locationComponent, {data: {mode:"add"}, width:"800px"})
    .afterClosed().subscribe(result=> {if (result)
      this.refreshList();});
  }
 edit(locationId)
  {
    this.matDialog.open(this.locationComponent, {data: {mode:"edit",locationId:locationId}, width:"800px"})
    .afterClosed().subscribe(result=> {if (result)
      this.refreshList();});
  }

  get(locationId)
    {
     this.matDialog.open(this.locationComponent, {data: {mode:"get",locationId:locationId}, width:"800px"})
        .afterClosed().subscribe(result=> {if (result)
          this.refreshList();});

    }
}
