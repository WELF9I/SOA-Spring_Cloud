import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RechercheParTitleComponent } from './recherche-par-title.component';

describe('RechercheParTitleComponent', () => {
  let component: RechercheParTitleComponent;
  let fixture: ComponentFixture<RechercheParTitleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RechercheParTitleComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RechercheParTitleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
