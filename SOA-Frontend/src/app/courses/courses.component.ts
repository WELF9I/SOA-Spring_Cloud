import { Component, OnInit } from '@angular/core';
import { Cours } from '../model/cours.model';
import { AuthService } from '../services/auth.service';
import { CoursService } from '../services/cours.service';
import { switchMap } from 'rxjs/operators';
import { Image } from "../model/image.model";

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html'
})
export class CoursesComponent implements OnInit {
  courses?: Cours[];

  constructor(
    private coursService: CoursService,
    public authService: AuthService
  ) { }

  ngOnInit(): void {
    this.chargerCours();
  }

  chargerCours() {
    this.authService.ensureAuthenticatedRequest().pipe(
      switchMap(() => this.coursService.listeCours())
    ).subscribe({
      next: (coursList) => {
        this.courses = coursList;
        this.courses.forEach((cours) => {
          // Check if there are any images and set imageStr to the first image's data
          if (cours.images && cours.images.length > 0) {
            const firstImage = cours.images[0];
            cours.imageStr = 'data:' + firstImage.type + ';base64,' + firstImage.image;
          } else {
            cours.imageStr = ''; // Set a default empty string or placeholder if no images
          }
        });
      },
      error: (error) => {
        console.error('Error loading courses:', error);
        if (error.status === 401) {
          this.authService.logout();
        }
      }
    });
  }

  supprimerCours(c: Cours) {
    let conf = confirm("Etes-vous sûr ?");
    if (conf) {
      this.authService.ensureAuthenticatedRequest().pipe(
        switchMap(() => this.coursService.supprimerCours(c.id))
      ).subscribe({
        next: () => {
          console.log("Cours supprimé");
          this.chargerCours();
        },
        error: (error) => {
          console.error('Error deleting course:', error);
          if (error.status === 401) {
            this.authService.logout();
          }
        }
      });
    }
  }
}
