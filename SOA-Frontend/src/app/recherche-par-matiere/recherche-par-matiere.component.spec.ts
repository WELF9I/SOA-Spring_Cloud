import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RechercheParMatiereComponent } from './recherche-par-matiere.component';

describe('RechercheParMatiereComponent', () => {
  let component: RechercheParMatiereComponent;
  let fixture: ComponentFixture<RechercheParMatiereComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RechercheParMatiereComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RechercheParMatiereComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
