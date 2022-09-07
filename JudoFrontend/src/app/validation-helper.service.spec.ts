import { TestBed } from '@angular/core/testing';

import { ValidationHelperService } from './validation-helper.service';

describe('ValidationHelperService', () => {
  let service: ValidationHelperService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ValidationHelperService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
