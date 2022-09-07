import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Page } from './page';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PageService {

  constructor(private http: HttpClient) { }

  getPages():Observable<Page[]>
  {
     return this.http.get<Page[]>(environment.apiUrl+'/page');
  }

  getPage(pageId:number):Observable<Page>
  {
     return this.http.get<Page>(environment.apiUrl+'/page/'+pageId);
  }

  savePage(dane:FormData, pageStaticId : number)
   {
      return this.http.put(environment.apiUrl+'/backoffice/page/'+pageStaticId,dane);
   }
   addPage(data: any):Observable<Page>
  {
     return this.http.post<Page>(environment.apiUrl+'/backoffice/page', data);
  }
  deletePage(pageStaticId : number)
  {
     return this.http.delete(environment.apiUrl+'/backoffice/page/'+pageStaticId);
  }
  getPageBackoffice(pageStaticId : number):Observable<Page>
  {
     return this.http.get<Page>(environment.apiUrl+'/backoffice/page/'+pageStaticId);
  }
  getPagesBackoffice():Observable<Page[]>
  {
     return this.http.get<Page[]>(environment.apiUrl+'/backoffice/page');
  }
}
