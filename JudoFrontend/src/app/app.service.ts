import { Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders,HttpInterceptor, HttpHandler, HttpRequest, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';
import { Observable } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';
import { environment } from 'src/environments/environment';

@Injectable()
export class AppService{
  wrongCredentials = false;
  token = null;
  waiting = false;
  helper = new JwtHelperService();

  claims = new Map();
  public checkAuthenticationToken()
  {
    if (this.token==null)
    this.authenticated = false;
    else
    {
      if (this.helper.isTokenExpired(this.token))
      {
       this.authenticated = false;
       for (var i in this.claims)
        this.claims[i] = null;
      }
    }
  }
   constructor(private http: HttpClient) {
    if (localStorage.getItem('access_token')!=null)
    {
      this.token = localStorage.getItem('access_token');
      this.decodedToken = this.helper.decodeToken(this.token);
      localStorage.setItem('access_token', this.token);
      this.wrongCredentials = false;
      for (var i in this.decodedToken)
        this.claims[i] = this.decodedToken[i];
      this.authenticated=true;
    }
  }
                
 
  private _authenticatedSource: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  public authenticated$: Observable<boolean> = this._authenticatedSource.asObservable();
  
  set authenticated(newValue:boolean) {
    this._authenticatedSource.next(newValue);
  }
 get authenticated():boolean {
    return this._authenticatedSource.value;
  }

  logout()
  {
    this.authenticated = false;
    this.token = null;
    localStorage.removeItem('access_token');
    for (var i in this.claims)
     this.claims[i] = null;
  }
  
  public decodedToken ;
  authenticate(credentials, callback) {

		const headers = new HttpHeaders()
  		  .set("Content-Type", "application/json");
        this.http.post(environment.apiUrl+'/authenticate',credentials, {headers: headers}).subscribe(
        response => {
            if (response['token']!=null) {
            setTimeout(() => {
        
                this.token = response['token'];
                this.decodedToken = this.helper.decodeToken(this.token);
                localStorage.setItem('access_token', this.token);
                this.wrongCredentials = false;
                this.authenticated=true;
                for (var i in this.decodedToken)
                 this.claims[i] = this.decodedToken[i];
             });
            } else {
                this.authenticated=false;
                this.wrongCredentials = true;
            }
            return callback && callback();
        },
        error =>
        {
     	   this.authenticated=false;
           this.wrongCredentials = true;
        }
        );

    }
    register(credentials, callback) {

      const headers = new HttpHeaders()
          .set("Content-Type", "application/json");
          this.http.post(environment.apiUrl+'/register',credentials, {headers: headers}).subscribe(
          response => {
              
              return callback && callback({success:true});
          },
          error =>
          {
              return callback && callback({success:false, error: error});
          }
          );
  
      }
      remainder(credentials, callback) {

        const headers = new HttpHeaders()
            .set("Content-Type", "application/json");
            this.http.post(environment.apiUrl+'/remainder',credentials, {headers: headers}).subscribe(
            response => {
                
                return callback && callback({success:true});
            },
            error =>
            {
                return callback && callback({success:false, error: error});
            }
            );
    
        }


        remainderEnterPassword(credentials,token, callback) {

          const headers = new HttpHeaders()
              .set("Content-Type", "application/json");
              this.http.post(environment.apiUrl+'/remainderEnterPassword?token='+token,credentials, {headers: headers}).subscribe(
              response => {
                  
                  return callback && callback({success:true});
              },
              error =>
              {
                  return callback && callback({success:false, error: error});
              }
              );
      
          }


      activate(token, callback) {

        const headers = new HttpHeaders()
            .set("Content-Type", "application/json");
            this.http.post(environment.apiUrl+'/activate?token='+token,null, {headers: headers}).subscribe(
            response => {
                
                return callback && callback({success:true});
            },
            error =>
            {
                return callback && callback({success:false, error: error});
            }
            );
    
        }
}
