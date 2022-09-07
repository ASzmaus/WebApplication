import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Discipline } from './discipline';

@Injectable({
  providedIn: 'root'
})
export class DisciplineService {
 constructor(private http: HttpClient) { }

//   saveDiscipline(dane:FormData, disciplineId : number)
//    {
//       return this.http.put(environment.apiUrl+'/discipline/'+disciplineId,dane);
//    }
//    addDiscipline(data: any):Observable<Discipline>
//   {
//      return this.http.post<Discipline>(environment.apiUrl+'/discipline', data);
//   }
//   deleteDiscipline(disciplineId : number)
//   {
//      return this.http.delete(environment.apiUrl+'/discipline/'+disciplineId);
//   }
//   getDiscipline(disciplineId : number):Observable<Discipline>
//   {
//      return this.http.get<Discipline>(environment.apiUrl+'/discipline/'+disciplineId);
//   }
  getDisciplines():Observable<Discipline[]>
  {
     return this.http.get<Discipline[]>( environment.apiUrl+'/discipline');

}
}
