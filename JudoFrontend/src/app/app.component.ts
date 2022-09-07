import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AppService } from './app.service';
import { PageService } from './page.service';
import { Page } from './page';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'JudoFrontend';

  constructor(public  app: AppService, private router: Router,public route: ActivatedRoute, public pageService: PageService)  {
  }
  logout() {
    this.app.logout();
  this.router.navigateByUrl('/login');

  }
  minFont  :number= 12;
  maxFont  :number= 30;
  resizeStep  :number= 2;


  fontSize :number= 16;

  applyFontSize()
  {
    document.documentElement.setAttribute("style","font-size:"+this.fontSize+"px;");
  }

  increase()
  {
    if(this.fontSize < this.maxFont){
      this.fontSize+=this.resizeStep;
    }
    this.applyFontSize();
  }

  decrease()
  {
    if(this.fontSize > this.minFont){
      this.fontSize-=this.resizeStep;
    }
    this.applyFontSize();
  }

  resetFontSize()
  {
    this.fontSize=16;
    this.applyFontSize();
  }

  public contrast : number = 0;
  toggleContrast()
  {
    this.contrast++;
    if (this.contrast>3)
      this.contrast = 0;
    if (this.contrast>0)
     document.getElementsByTagName("body")[0].setAttribute("class","contrast"+this.contrast);
    else
     document.getElementsByTagName("body")[0].setAttribute("class","");
  }

  public pages: Page[];

  ngOnInit(): void {


  setTimeout(
    () =>
        {
            this.pageService.getPages().subscribe(
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

}
