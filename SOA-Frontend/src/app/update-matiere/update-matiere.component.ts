import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Matiere } from '../model/matiere.model';
import { CoursService } from '../services/cours.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-update-matiere',
  templateUrl: './update-matiere.component.html',
  styles: []
})
export class UpdateMatiereComponent implements OnInit {

  @Input() matiere!: Matiere; 
  @Input() ajout!: boolean; 
  @Output() matiereUpdated = new EventEmitter<Matiere>();

  constructor(private coursService: CoursService) { } 

  ngOnInit(): void {
    console.log("ngOnInit du composant UpdateMatiere", this.matiere);
  }

  saveMatiere() {
    if (this.ajout) {
      this.coursService.ajouterMatiere(this.matiere).subscribe(
        (response: Matiere) => {
          console.log('Matiere added:', response);
          this.matiereUpdated.emit(response); 
        },
        (error: HttpErrorResponse) => {
          console.error('Error adding matiere:', error);
        }
      );
    } else {
      this.coursService.updateMatiere(this.matiere).subscribe(
        (response: Matiere) => {
          console.log('Matiere updated:', response);
          this.matiereUpdated.emit(response);
        },
        (error: HttpErrorResponse) => {
          console.error('Error updating matiere:', error);
        }
      );
    }
  }
}