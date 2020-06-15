import { TestBed } from '@angular/core/testing';

import { SystemhealthService } from './systemhealth.service';

describe('SystemhealthService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SystemhealthService = TestBed.get(SystemhealthService);
    expect(service).toBeTruthy();
  });
});
