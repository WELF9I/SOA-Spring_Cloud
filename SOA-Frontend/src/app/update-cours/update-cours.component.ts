import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Matiere } from '../model/matiere.model';
import { Cours } from '../model/cours.model';
import { CoursService } from '../services/cours.service';
import { Image } from "../model/image.model";

@Component({
  selector: 'app-update-cours',
  templateUrl: './update-cours.component.html',
  styles: []
})
export class UpdateCoursComponent implements OnInit {

  currentCours = new Cours();
  matieres!: Matiere[]; 
  updatedMatiereId!: number; 
  myImage! : string; 
  uploadedImage!: File; 
  isImageUpdated: Boolean = false;

  constructor(private activatedRoute: ActivatedRoute,
              private router: Router,
              private coursService: CoursService) { }

  ngOnInit(): void {
    this.coursService.listeMatieres().subscribe(mats => {
      this.matieres = mats; 
    }); 
 
    this.coursService.consulterCours(this.activatedRoute.snapshot.params['id'])
      .subscribe(cours => {
        this.currentCours = cours; 
        this.updatedMatiereId = cours.matiere.id; 
      }); 
  }

  updateCours() {
    this.currentCours.matiere = this.matieres.find(mat => mat.id == this.updatedMatiereId)!;         
    this.coursService.updateCours(this.currentCours).subscribe(() => { 
      this.router.navigate(['courses']); 
    }); 
  }

  onAddImageCours() { 
    this.coursService.uploadImageCours(this.uploadedImage, this.uploadedImage.name, this.currentCours.id)
      .subscribe((img: Image) => { 
        this.currentCours.images.push(img); 
      }); 
  } 

  supprimerImage(img: Image) { 
    let conf = confirm("Etes-vous sûr ?"); 
    if (conf) {
      this.coursService.supprimerImage(img.idImage).subscribe(() => { 
        const index = this.currentCours.images.indexOf(img, 0); 
        if (index > -1) { 
          this.currentCours.images.splice(index, 1); 
        } 
      }); 
    }
  }

  onImageUpload(event: any) { 
    if (event.target.files && event.target.files.length) { 
      this.uploadedImage = event.target.files[0]; 
      this.isImageUpdated = true; 
      const reader = new FileReader(); 
      reader.readAsDataURL(this.uploadedImage); 
      reader.onload = () => { 
        this.myImage = reader.result as string;  
      }; 
    } 
  } 
}