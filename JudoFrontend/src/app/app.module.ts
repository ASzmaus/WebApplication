import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Injectable } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppService } from './app.service';
import { DisciplineService } from './discipline.service';
import { LocationService } from './location.service';
import { ClubLocationService} from './location-club.service';
import { WorkGroupService } from './workGroup.service';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule, ReactiveFormsModule, ValidationErrors, AbstractControl } from '@angular/forms';
import { HttpInterceptor, HttpHandler, HttpRequest, HTTP_INTERCEPTORS } from '@angular/common/http';
import { HttpClientModule,HttpEvent } from '@angular/common/http';
import { AppComponent } from './app.component';
import { XhrInterceptor } from './xhr.interceptor';
import { LoadingInterceptor } from './loading.interceptor';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { RegistrationSuccessComponent } from './registration-success/registration-success.component';
import { MatDialogModule } from '@angular/material/dialog';
import { TextInputFieldComponent } from './text-input-field/text-input-field.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs';
import { MatAutocompleteModule} from '@angular/material/autocomplete';
import { ReadOnlyTextInputFieldComponent } from './read-only-text-input-field/read-only-text-input-field.component';
import { TextInputFieldCellComponent } from './text-input-field-cell/text-input-field-cell.component';
import { PageComponent } from './page/page.component';
import { AlertComponent } from './alert/alert.component';
import { MessageComponent } from './message/message.component';
import { ConfirmComponent } from './confirm/confirm.component';
import { ReadOnlyTextareaComponent } from './read-only-textarea/read-only-textarea.component';
import { ActivateComponent } from './activate/activate.component';
import { PagesBackofficeComponent } from './pages-backoffice/pages-backoffice.component';
import { PageBackofficeComponent } from './page-backoffice/page-backoffice.component';
import { TextareaFieldComponent } from './textarea-field/textarea-field.component';
import { RemainderComponent } from './remainder/remainder.component';
import { RemainderEnterPasswordComponent } from './remainder-enter-password/remainder-enter-password.component';
import { AutosizeModule} from 'ngx-autosize';
import { UserComponent } from './user/user.component';
import { UsersComponent } from './users/users.component';
import { ClubComponent } from './club/club.component';
import { ClubsComponent } from './clubs/clubs.component';
import { DisciplinesComponent } from './disciplines/disciplines.component';
import { DisciplineComponent } from './discipline/discipline.component';
import { WorkGroupsComponent } from './workGroups/workGroups.component';
import { WorkGroupComponent } from './workGroup/workGroup.component';
import { LocationsComponent } from './locations/locations.component';
import { LocationComponent } from './location/location.component';
import { DisciplinesClubComponent } from './disciplines-club/disciplines-club.component';
import { DisciplineClubComponent } from './discipline-club/discipline-club.component';
import { LocationsClubComponent } from './locations-club/locations-club.component';
import { ClubLocationComponent } from './location-club/location-club.component';
const routes: Routes = [];


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    RegistrationSuccessComponent,
    TextInputFieldComponent,
    ReadOnlyTextInputFieldComponent,
    TextInputFieldCellComponent,
    PageComponent,
    AlertComponent,
    MessageComponent,
    ConfirmComponent,
    ReadOnlyTextareaComponent,
    ActivateComponent,
    PagesBackofficeComponent,
    PageBackofficeComponent,
    TextareaFieldComponent,
    RemainderComponent,
    RemainderEnterPasswordComponent,
    DisciplinesComponent,
    DisciplineComponent,
    LocationsComponent,
    LocationComponent,
    WorkGroupsComponent,
    WorkGroupComponent,
    DisciplinesClubComponent,
    DisciplineClubComponent,
    LocationsClubComponent,
    ClubLocationComponent,
    ClubComponent,
    ClubsComponent,
    UserComponent,
    UsersComponent
  ],
  imports: [

    AutosizeModule,
    FormsModule,ReactiveFormsModule,MatAutocompleteModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule.forRoot(routes),
    BrowserAnimationsModule,
    MatDialogModule,
    NgbModule
  ],
  providers: [AppService, DisciplineService, LocationService, ClubLocationService, { provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true }
    , { provide: HTTP_INTERCEPTORS, useClass: LoadingInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

  }

