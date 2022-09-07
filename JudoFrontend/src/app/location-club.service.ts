import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ClubLocation } from './location-club';

@Injectable({
  providedIn: 'root'
})
export class ClubLocationService {
 constructor(private http: HttpClient) { }

   addClubLocation(data: any, clubId: number, locationId: number):Observable<ClubLocation>
  {
     return this.http.post<ClubLocation>(environment.apiUrl +'/club/'+ clubId +'/location/' + locationId, data);
  }
  deleteClubLocation(clubId: number, locationId: number)
  {
     return this.http.delete(environment.apiUrl +'/club/'+ clubId +'/location/' + locationId);
  }

  getLocationsClub(clubId:number):Observable<ClubLocation[]>
  {
     return this.http.get<ClubLocation[]>( environment.apiUrl+'/club/'+ clubId + '/location');
  }
}
