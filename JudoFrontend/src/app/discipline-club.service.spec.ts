import { TestBed } from '@angular/core/testing';

import { DisciplineService } from './discipline-club.service';

describe('DisciplineClubService', () => {
  let service: DisciplineClubService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DisciplineClubService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
