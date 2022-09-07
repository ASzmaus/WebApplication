import { Component, OnInit } from '@angular/core';
import { Page } from '../page';
import { PageService } from '../page.service';
import { ActivatedRoute, Router, NavigationStart, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-page',
  templateUrl: './page.component.html',
  styleUrls: ['./page.component.scss']
})
export class PageComponent implements OnInit {

  constructor(private pageService: PageService,private route: ActivatedRoute, private router: Router) {
    router.events.forEach((event) => {
      if(event instanceof NavigationEnd) {
        this.load();
      }
    });
   }

  pageId : number;
  page: Page;
  ngOnInit(): void {
    this.load();
  }

  public load()
  {
    this.pageId = parseInt(this.route.snapshot.paramMap.get('pageId'));
    this.pageService.getPage(this.pageId).subscribe(
      response => {
        this.page=response;
  },
  error =>
  {
  alert(error);
  }
  );

  }

}
