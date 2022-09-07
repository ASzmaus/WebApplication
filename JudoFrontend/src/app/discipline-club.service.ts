import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { DisciplineClub } from './discipline-club';

@Injectable({
  providedIn: 'root'
})
export class DisciplineClubService {
 constructor(private http: HttpClient) { }

   addDisciplineClub(data: any, clubId: number, disciplineId: number):Observable<DisciplineClub>
  {
     return this.http.post<DisciplineClub>(environment.apiUrl +'/club/'+ clubId +'/discipline/' + disciplineId, data);
  }

  deleteDisciplineClub(clubId: number, disciplineId: number)
  {
     return this.http.delete(environment.apiUrl +'/club/'+ clubId +'/discipline/' + disciplineId);
  }

  getDisciplinesClub(clubId:number):Observable<DisciplineClub[]>
  {
     return this.http.get<DisciplineClub[]>( environment.apiUrl+'/club/'+ clubId + '/discipline');
  }
}
