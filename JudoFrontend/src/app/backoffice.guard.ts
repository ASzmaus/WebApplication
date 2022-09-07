import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AppService } from './app.service';

@Injectable({
  providedIn: 'root'
})
export class BackofficeGuard implements CanActivate {
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      this.app.checkAuthenticationToken();
      if (!this.app.authenticated) {
        this.router.navigate(['login']);
        return false;
      }
      if (!this.app.claims["ROLE_o"] && !this.app.claims["ROLE_a"]) {
        this.router.navigate(['home']);
        return false;
      }
      return true;
  }

  constructor(public  app: AppService, public router: Router)  {
  }
}
