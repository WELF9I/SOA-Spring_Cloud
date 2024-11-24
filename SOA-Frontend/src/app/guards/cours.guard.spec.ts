import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { CoursGuard } from './cours.guard';

describe('CoursGuard', () => {
  let guard: CoursGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(CoursGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
