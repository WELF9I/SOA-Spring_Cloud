import { Component, OnInit } from '@angular/core';
import { Cours } from '../model/cours.model';
import { CoursService } from '../services/cours.service';

@Component({
  selector: 'app-recherche-par-title',
  templateUrl: './recherche-par-title.component.html',
  styles: []
})
export class RechercheParTitleComponent implements OnInit {
  title: string = '';
  courses: Cours[] = [];
  allCourses: Cours[] = [];
  searchTerm: string = '';

  constructor(private coursService: CoursService) { }

  ngOnInit(): void {
    this.coursService.listeCours().subscribe(items => {
      console.log(items);
      this.courses = items;
      this.allCourses = items; 
    });
  }

  onKeyUp(filterText: string) {
    this.searchTerm = filterText;
    this.filterCourses();
  }

  filterCourses() {
    if (!this.searchTerm) {
      this.courses = this.allCourses;
    } else {
      this.courses = this.allCourses.filter(course =>
        course.title.toLowerCase().includes(this.searchTerm.toLowerCase())
      );
    }
  }
}