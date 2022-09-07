import { Injectable } from "@angular/core";
import { AbstractControl, AsyncValidatorFn, ValidationErrors } from "@angular/forms";
import { Observable, of, timer } from "rxjs";
import { map, debounceTime, take, switchMap, tap } from "rxjs/operators";

import { LoginAvailable } from './login-available';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';

function isEmptyInputValue(value: any): boolean {
  // we don't check for string here so it also works with arrays
  return value === null || value.length === 0;
}

@Injectable({
  providedIn: "root"
})
export class UniqueEmailValidator {
  constructor(private http: HttpClient) {}
  existingEmailValidator(): AsyncValidatorFn {
    return (control: AbstractControl): Promise<ValidationErrors | null> | Observable<ValidationErrors | null> => {
        var credentials = {login:control.value};
        const headers = new HttpHeaders()
        .set("Content-Type", "application/json");

        return timer(500)
        .pipe(
            switchMap(() => this.http.post(environment.apiUrl+'/checkLoginAvailable',credentials, {headers: headers})
                    .pipe(
                        map((user: LoginAvailable[]) =>
                         !user[0].available ? { loginUnique: true }
                         : null)
                    )
            )
        );
    };
  }
}
