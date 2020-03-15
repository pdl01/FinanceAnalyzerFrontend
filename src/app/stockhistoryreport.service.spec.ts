import { TestBed } from '@angular/core/testing';

import { StockhistoryreportService } from './stockhistoryreport.service';

describe('StockhistoryreportService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: StockhistoryreportService = TestBed.get(StockhistoryreportService);
    expect(service).toBeTruthy();
  });
});
