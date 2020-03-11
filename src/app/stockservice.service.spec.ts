import { TestBed } from '@angular/core/testing';

import { StockserviceService } from './stockservice.service';

describe('StockserviceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: StockserviceService = TestBed.get(StockserviceService);
    expect(service).toBeTruthy();
  });
});
