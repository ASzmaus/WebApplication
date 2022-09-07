import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Location } from './location';

@Injectable({
  providedIn: 'root'
})
export class LocationService {
 constructor(private http: HttpClient) { }

  saveLocation(dane:FormData, locationId : number)
   {
      return this.http.put(environment.apiUrl+'/location/'+ locationId,dane);
   }
   addLocation(data: any):Observable<Location>
  {
     return this.http.post<Location>(environment.apiUrl+'/location', data);
  }

  getLocation( locationId : number):Observable<Location>
  {
     return this.http.get<Location>(environment.apiUrl+'/location/'+ locationId);
  }
  getLocations():Observable<Location[]>
  {
     return this.http.get<Location[]>( environment.apiUrl+'/location');

  }
}
