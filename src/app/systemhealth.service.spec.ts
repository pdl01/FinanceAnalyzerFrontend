import { TestBed } from '@angular/core/testing';

import { SystemHealthService } from './systemhealth.service';

describe('SystemHealthService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SystemHealthService = TestBed.get(SystemHealthService);
    expect(service).toBeTruthy();
  });
});
