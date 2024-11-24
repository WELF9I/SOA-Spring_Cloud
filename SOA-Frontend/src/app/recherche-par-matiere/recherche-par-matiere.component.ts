import { Component, OnInit } from '@angular/core';
import { Matiere } from '../model/matiere.model';
import { Cours } from '../model/cours.model';
import { CoursService } from '../services/cours.service';

@Component({
  selector: 'app-recherche-par-matiere',
  templateUrl: './recherche-par-matiere.component.html',
  styles: []
})
export class RechercheParMatiereComponent implements OnInit {
  IdMatiere: number = 0; 
  matieres!: Matiere[];
  courses!: Cours[];

  constructor(private coursService: CoursService) { }

  ngOnInit(): void {
    this.coursService.listeMatieres().subscribe(matiere => {
      this.matieres = matiere;
      if (this.matieres.length > 0) {
        this.IdMatiere = this.matieres[0].id;
        this.onChange(); 
      }
    });
  }

  onChange() {
    if (this.IdMatiere) {
      this.coursService.rechercherParMatiere(this.IdMatiere).subscribe(courses => {
        this.courses = courses;
      });
    }
  }
}
