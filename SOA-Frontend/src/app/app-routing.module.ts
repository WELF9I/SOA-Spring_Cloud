import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddCoursComponent } from './add-cours/add-cours.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { ListeMatieresComponent } from './liste-matieres/liste-matieres.component';
import { LoginComponent } from './login/login.component';
import { coursGuard } from './guards/cours.guard'; 
import { CoursesComponent } from './courses/courses.component';
import { RechercheParMatiereComponent } from './recherche-par-matiere/recherche-par-matiere.component';
import { RechercheParTitleComponent } from './recherche-par-title/recherche-par-title.component';
import { UpdateCoursComponent } from './update-cours/update-cours.component';import { RegisterComponent } from './register/register.component';
import { VerifEmailComponent } from './verif-email/verif-email.component';
;

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path:'register',component:RegisterComponent},
  { path: 'verifEmail', component: VerifEmailComponent },
  {path: "courses", component: CoursesComponent},
  {path: "add-cours", component: AddCoursComponent, canActivate: [coursGuard]},
  {path: "updateCours/:id", component: UpdateCoursComponent},
  {path: "rechercheParMatiere", component: RechercheParMatiereComponent},
  {path: "rechercheParTitle", component: RechercheParTitleComponent},
  {path: "listeMatieres", component: ListeMatieresComponent},
  {path: 'app-forbidden', component: ForbiddenComponent},
  {path: "", redirectTo: "courses", pathMatch: "full"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
