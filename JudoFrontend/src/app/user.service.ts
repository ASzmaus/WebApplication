import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  saveAdmin(dane:FormData, clubId : number, userId : number)
   {
      return this.http.put(environment.apiUrl +`/club`+`/`+ `${clubId}` + `/`+`administrator`+`/`+ `${userId}`, dane);
   }
   addAdmin(data: any, clubId : number):Observable<User>
  {
     return this.http.post<User>(environment.apiUrl +`/club`+`/`+ `${clubId}` + `/`+`administrator`, data);
  }
  deleteAdmin( userId : number)
  {
     return this.http.delete(environment.apiUrl+'/administrator/'+ userId);
  }
  getAdmin(clubId : number, userId : number):Observable<User>
  {
     return this.http.get<User>(environment.apiUrl +`/club`+`/`+ `${clubId}` + `/`+`administrator`+`/`+ `${userId}`);
  }
  getAdmins(clubId : number):Observable<User[]>
  {
     return this.http.get<User[]>(environment.apiUrl+`/club`+`/`+ `${clubId}` + `/`+`administrator`);
  }

  saveStaff(dane:FormData, clubId : number, staffId : number)
   {
      return this.http.put(environment.apiUrl +`/club`+`/`+ `${clubId}` + `/`+`staff`+`/`+ `${staffId}`, dane);
   }

   addStaff(data: any, clubId : number):Observable<User>
   {
      return this.http.post<User>(environment.apiUrl +`/club`+`/`+ `${clubId}` + `/`+`staff`, data);
   }

   deleteStaff( staffId : number)
   {
      return this.http.delete(environment.apiUrl+'/staff/'+ staffId);
   }

   getStaff(clubId : number, staffId : number):Observable<User>
   {
      return this.http.get<User>(environment.apiUrl +`/club`+`/`+ `${clubId}` + `/`+`staff`+`/`+ `${staffId}`);
   }

   getStaffs(clubId : number):Observable<User[]>
   {
      return this.http.get<User[]>(environment.apiUrl+`/club`+`/`+ `${clubId}` + `/`+`staff`);
   }

}
