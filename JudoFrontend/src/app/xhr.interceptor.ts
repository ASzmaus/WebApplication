import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { AppService } from './app.service';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable()
export class XhrInterceptor implements HttpInterceptor {

  constructor(private app: AppService) {
  }
  intercept(req: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    var headers = req.headers;
    if (this.app.token!=null)
     headers = headers.set('Authorization', 'Bearer '+this.app.token);
    const xhr = req.clone({
      headers: headers.set('X-Requested-With', 'XMLHttpRequest')
    });
    
    return next.handle(xhr).pipe(catchError(err => {
      if ([401, 403].includes(err.status) ) {
        this.app.logout();
        return throwError(err);
      }

      const error = (err && err.error && err.error.message) || err.statusText;
      return throwError(error);
  }));
  }
}
