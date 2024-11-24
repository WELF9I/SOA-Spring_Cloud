import { Injectable } from '@angular/core';
import { Matiere } from '../model/matiere.model';
import { Cours } from '../model/cours.model';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Image } from '../model/image.model'; 

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

const apiURL = 'http://localhost:8888/api';

@Injectable({
  providedIn: 'root'
})
export class CoursService {
  imageAPI: string = apiURL + '/image';
  apiURLCours: string = apiURL + '/cours';
  apiURLMatiere: string = apiURL + '/matieres';
  courses: Cours[] = [];
  matieres: Matiere[] = [];

  constructor(private http: HttpClient) { }

  // Cours related methods
  listeCours(): Observable<Cours[]> {
    return this.http.get<Cours[]>(this.apiURLCours);
  }

  ajouterCours(cours: Cours): Observable<Cours> {
    return this.http.post<Cours>(this.apiURLCours, cours);
  }

  supprimerCours(id: number) {
    const url = `${this.apiURLCours}/${id}`;
    return this.http.delete(url);
  }

  consulterCours(id: number): Observable<Cours> {
    const url = `${this.apiURLCours}/${id}`;
    return this.http.get<Cours>(url);
  }

  updateCours(cours: Cours): Observable<Cours> {
    const url = `${this.apiURLCours}/${cours.id}`;
    return this.http.put<Cours>(url, cours);
  }

  rechercherParMatiere(idMatiere: number): Observable<Cours[]> {
    const url = `${this.apiURLCours}/matiere/${idMatiere}`;
    return this.http.get<Cours[]>(url);
  }

  // Matiere related methods
  listeMatieres(): Observable<Matiere[]> {
    return this.http.get<Matiere[]>(this.apiURLMatiere);
  }

  ajouterMatiere(matiere: Matiere): Observable<Matiere> {
    return this.http.post<Matiere>(this.apiURLMatiere, matiere);
  }

  getMatiereById(id: number): Observable<Matiere> {
    return this.http.get<Matiere>(`${this.apiURLMatiere}/${id}`);
  }

  updateMatiere(matiere: Matiere): Observable<Matiere> {
    return this.http.put<Matiere>(`${this.apiURLMatiere}/${matiere.id}`, matiere);
  }

  supprimerMatiere(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiURLMatiere}/${id}`);
  }

  findMatieresByName(name: string): Observable<Matiere[]> {
    return this.http.get<Matiere[]>(`${this.apiURLMatiere}/search/name?name=${name}`);
  }

  findMatieresByNameContains(name: string): Observable<Matiere[]> {
    return this.http.get<Matiere[]>(`${this.apiURLMatiere}/search/nameContains?name=${name}`);
  }

  sortMatieresByName(): Observable<Matiere[]> {
    return this.http.get<Matiere[]>(`${this.apiURLMatiere}/sort`);
  }

  uploadImage(file: File, filename: string): Observable<Image> {
    const imageFormData = new FormData();
    imageFormData.append('image', file, filename);
    const url = `${this.imageAPI + '/upload'}`;
    return this.http.post<Image>(url, imageFormData);
  }

  uploadImageCours(file: File, filename: string, idCours: number): Observable<any> {
    const imageFormData = new FormData();
    imageFormData.append('image', file, filename);
    const url = `${this.imageAPI + '/uplaodImageCours'}/${idCours}`;
    return this.http.post(url, imageFormData);
  }

  supprimerImage(id: number) {
    const url = `${this.imageAPI}/delete/${id}`;
    return this.http.delete(url, httpOptions);
  }

  loadImage(id: number): Observable<Image> {
    const url = `${apiURL + '/image/get/info'}/${id}`;
    return this.http.get<Image>(url);
  }
}

