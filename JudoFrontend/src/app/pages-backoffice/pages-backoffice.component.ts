import { Component, OnInit } from '@angular/core';
import { PageService } from '../page.service';
import { Page } from '../page';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmComponent } from '../confirm/confirm.component';
import { PageBackofficeComponent } from '../page-backoffice/page-backoffice.component';

@Component({
  selector: 'app-pages-backoffice',
  templateUrl: './pages-backoffice.component.html',
  styleUrls: ['./pages-backoffice.component.scss']
})
export class PagesBackofficeComponent implements OnInit {


  constructor(private pageService: PageService,   private matDialog: MatDialog) { }
  pages : Page[];

  confirm = ConfirmComponent;
  pageBackofficeComponent = PageBackofficeComponent;
  ngOnInit(): void {
    this.refreshList();
  }

  refreshList()
  {
    setTimeout(
      () =>
          {
                  this.pageService.getPagesBackoffice().subscribe(
                    response => {
                      this.pages=response;
                      },
                      error =>
                      {
                        alert(error);
                      }
                    );
          }
        );
  }

  delete(id)
  {
    this.matDialog.open(this.confirm, {data: "Are you sure you want to delete this page?"})
    .afterClosed().subscribe(result=> {if (result)
      this.pageService.deletePage(id).subscribe(response=>{this.refreshList();});});

  }

  add()
  {
    this.matDialog.open(this.pageBackofficeComponent, {data: {mode:"add"}, width:"800px"})
    .afterClosed().subscribe(result=> {if (result)
      this.refreshList();});

  }

  edit(id)
  {
    this.matDialog.open(this.pageBackofficeComponent, {data: {mode:"edit",id:id}, width:"800px"})
    .afterClosed().subscribe(result=> {if (result)
      this.refreshList();});

  }

}
