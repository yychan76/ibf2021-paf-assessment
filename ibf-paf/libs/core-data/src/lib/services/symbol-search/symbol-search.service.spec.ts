import { TestBed } from '@angular/core/testing';

import { SymbolSearchService } from './symbol-search.service';

describe('SymbolSearchService', () => {
  let service: SymbolSearchService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SymbolSearchService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
