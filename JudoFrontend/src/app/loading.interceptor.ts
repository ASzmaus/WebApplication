import { Injectable } from '@angular/core';
import {finalize,catchError,tap} from 'rxjs/operators';
import { AppService } from './app.service';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class LoadingInterceptor implements HttpInterceptor {
  private counter : 0;
  constructor(private app: AppService
  
  ) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
	this.counter++;
     this.app.waiting = true;
    
	return next.handle(req).pipe(
      finalize(() => {
         this.counter--;
        if (this.counter > 0) return;
        this.app.waiting = false;
    
        
      })
    );
  }
}
