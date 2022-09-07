import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AuthGuard } from './auth.guard';
import {BackofficeGuard } from './backoffice.guard';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { PageComponent } from './page/page.component';
import { ActivateComponent } from './activate/activate.component';
import { PagesBackofficeComponent } from './pages-backoffice/pages-backoffice.component';
import { RemainderEnterPasswordComponent } from './remainder-enter-password/remainder-enter-password.component';
import { RemainderComponent } from './remainder/remainder.component';
import { DisciplinesComponent } from './disciplines/disciplines.component';
import { LocationsComponent } from './locations/locations.component';
import { LocationComponent } from './location/location.component';
import { DisciplinesClubComponent } from './disciplines-club/disciplines-club.component';
import { DisciplineClubComponent } from './discipline-club/discipline-club.component';
import { LocationsClubComponent } from './locations-club/locations-club.component';
import { ClubLocationComponent } from './location-club/location-club.component';
import { DisciplineComponent } from './discipline/discipline.component';
import { ClubsComponent } from './clubs/clubs.component';
import { ClubComponent } from './club/club.component';
import { UsersComponent } from './users/users.component';
import { UserComponent } from './user/user.component';
import { WorkGroupsComponent } from './workGroups/workGroups.component';
import { WorkGroupComponent } from './workGroup/workGroup.component';


const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'home'},
  { path: 'home', component: HomeComponent},
  { path: 'login', component: LoginComponent},
  { path: 'remainder', component: RemainderComponent},
  { path: 'remainderEnterPassword/:token', component: RemainderEnterPasswordComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'activate/:token', component: ActivateComponent},
  { path: 'page/:pageId', component: PageComponent},
  { path: 'discipline', component: DisciplinesComponent, canActivate: [BackofficeGuard]},
  { path: 'location', component: LocationsComponent, canActivate: [BackofficeGuard]},
  { path: 'location', component: LocationComponent, canActivate: [BackofficeGuard]},
  { path: 'location/:locationId', component: LocationComponent, canActivate: [BackofficeGuard]},
  { path: 'club', component: ClubsComponent, canActivate: [BackofficeGuard]},
  { path: 'club/:clubId', component: ClubComponent, canActivate: [BackofficeGuard]},
  { path: 'backoffice-pages', component: PagesBackofficeComponent, canActivate: [BackofficeGuard]},
//     { path: 'club/:clubId/discipline/:disciplineId/workGroup/:workGroupId', component: WorkGroupComponent, canActivate: [BackofficeGuard]},
  { path: 'club/:clubId/administrator/:userId', component: UserComponent, canActivate: [BackofficeGuard]},
  { path: 'club/:clubId/administrator', component: UsersComponent, canActivate: [BackofficeGuard]},
  { path: 'club/:clubId/staff/:staffId', component: UserComponent, canActivate: [BackofficeGuard]},
  { path: 'club/:clubId/staff', component: UsersComponent, canActivate: [BackofficeGuard]},
  { path: 'club/:clubId/administrator', component: UserComponent, canActivate: [BackofficeGuard]},
  { path: 'club/:clubId/discipline', component: DisciplinesClubComponent, canActivate: [BackofficeGuard]},
  { path: 'club/:clubId/discipline/:disciplineId', component:  DisciplineClubComponent, canActivate: [BackofficeGuard]},
  { path: 'club/:clubId/location', component: LocationsClubComponent, canActivate: [BackofficeGuard]},
  { path: 'club/:clubId/location/:locationId', component:  ClubLocationComponent, canActivate: [BackofficeGuard]},
  { path: 'club/:clubId/workGroup', component: WorkGroupsComponent, canActivate: [BackofficeGuard]}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
