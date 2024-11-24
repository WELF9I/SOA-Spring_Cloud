import { Component, OnInit } from '@angular/core';
import { Matiere } from '../model/matiere.model';
import { CoursService } from '../services/cours.service';

@Component({
  selector: 'app-liste-matieres',
  templateUrl: './liste-matieres.component.html',
  styles: []
})
export class ListeMatieresComponent implements OnInit {

  matieres!: Matiere[];
  ajout: boolean = true;
  updatedMatiere: Matiere = { "id": 0, "code": "", "description": "", "name": "", "teacherName": "" };

  constructor(private coursService: CoursService) { }

  ngOnInit(): void {
    this.chargerMatieres();
  }

  chargerMatieres() {
    this.coursService.listeMatieres().subscribe(mats => {
      this.matieres = mats;
      console.log(mats);
    });
  }

  matiereUpdated(matiere: Matiere) {
    console.log("Matière reçue du composant updateMatiere: ", matiere);
    if (this.ajout) {
      this.coursService.ajouterMatiere(matiere).subscribe(() => {
        this.chargerMatieres();
        this.resetForm();
      });
    } else {
      this.coursService.updateMatiere(matiere).subscribe(() => {
        this.chargerMatieres();
        this.resetForm();
      });
    }
  }

  updateMatiere(matiere: Matiere) {
    this.updatedMatiere = {...matiere};
    this.ajout = false;
  }

  supprimerMatiere(matiere: Matiere) {
    if(confirm("Êtes-vous sûr de vouloir supprimer cette matière ?")) {
      this.coursService.supprimerMatiere(matiere.id).subscribe(() => {
        this.chargerMatieres();
      });
    }
  }

  resetForm() {
    this.updatedMatiere = { "id": 0, "code": "", "description": "", "name": "", "teacherName": "" };
    this.ajout = true;
  }
}