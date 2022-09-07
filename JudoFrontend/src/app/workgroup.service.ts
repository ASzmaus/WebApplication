import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { WorkGroup } from './workGroup';

@Injectable({
  providedIn: 'root'
})
export class WorkGroupService {

 constructor(private http: HttpClient) { }

  addGroupToClubToDiscipline(data: any, clubId: number, disciplineId: number):Observable<WorkGroup>
  {
     return this.http.post<WorkGroup>(environment.apiUrl +'/club/'+ clubId +'/discipline/' + disciplineId + '/workGroup', data);
  }

  saveGroup(dane:FormData, clubId: number, disciplineId: number,  workGroupId: number)
  {
     return this.http.put(environment.apiUrl +'/club/'+ clubId +'/discipline/' + disciplineId + '/workGroup/'+ workGroupId, dane);
  }

  getWorkGroupsByClub(clubId:number):Observable<WorkGroup[]>
  {
     return this.http.get<WorkGroup[]>( environment.apiUrl+'/club/'+ clubId + '/workGroup');
  }

  getWorkGroup(clubId:number, workGroupId:number):Observable<WorkGroup>
  {
     return this.http.get<WorkGroup>( environment.apiUrl+'/club/'+ clubId + '/workGroup/' + workGroupId);
  }
}
