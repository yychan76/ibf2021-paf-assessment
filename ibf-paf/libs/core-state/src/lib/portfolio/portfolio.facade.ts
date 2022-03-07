import { Injectable } from '@angular/core';
import { select, Store, Action } from '@ngrx/store';

import * as PortfolioActions from './portfolio.actions';
import * as PortfolioFeature from './portfolio.reducer';
import * as PortfolioSelectors from './portfolio.selectors';

@Injectable()
export class PortfolioFacade {
  /**
   * Combine pieces of state using createSelector,
   * and expose them as observables through the facade.
   */
  loaded$ = this.store.pipe(select(PortfolioSelectors.getPortfolioLoaded));
  allPortfolio$ = this.store.pipe(select(PortfolioSelectors.getAllPortfolio));
  selectedPortfolio$ = this.store.pipe(select(PortfolioSelectors.getSelected));

  constructor(private readonly store: Store) {}

  /**
   * Use the initialization action to perform one
   * or more tasks in your Effects.
   */
  init() {
    this.store.dispatch(PortfolioActions.init());
  }
}
