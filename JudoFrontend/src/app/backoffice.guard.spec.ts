import { TestBed } from '@angular/core/testing';

import { BackofficeGuard } from './backoffice.guard';

describe('BackofficeGuard', () => {
  let guard: BackofficeGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(BackofficeGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
