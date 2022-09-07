import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Club } from './club';

@Injectable({
  providedIn: 'root'
})
export class ClubService {
 constructor(private http: HttpClient) { }

  saveClub(dane:FormData, clubId : number)
   {
      return this.http.put(environment.apiUrl+'/club/'+clubId,dane);
   }
   addClub(data: any):Observable<Club>
  {
     return this.http.post<Club>(environment.apiUrl+'/club', data);
  }
  deleteClub(clubId : number)
  {
     return this.http.delete(environment.apiUrl+'/club/'+clubId);
  }
  getClub(clubId : number):Observable<Club>
  {
     return this.http.get<Club>(environment.apiUrl+'/club/'+clubId);
  }
  getClubs():Observable<Club[]>
  {
     return this.http.get<Club[]>( environment.apiUrl+'/club');

}
}
