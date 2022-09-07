import { Component, OnInit } from '@angular/core';
import { AppService } from '../app.service';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { PageService } from '../page.service';

@Component({
  templateUrl: './home.component.html'
})
export class HomeComponent  implements OnInit {
  constructor(private router: Router,public route: ActivatedRoute, public pageService: PageService) {
  }

  ngOnInit(): void {


    setTimeout(
      () =>
          {
              this.pageService.getPages().subscribe(
                  response => {
                    this.router.navigate(['page/'+response[0].pageStaticId]);
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
