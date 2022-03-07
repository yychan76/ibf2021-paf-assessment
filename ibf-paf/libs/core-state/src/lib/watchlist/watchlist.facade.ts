import { Injectable } from '@angular/core';
import { select, Store, Action } from '@ngrx/store';

import * as WatchlistActions from './watchlist.actions';
import * as WatchlistFeature from './watchlist.reducer';
import * as WatchlistSelectors from './watchlist.selectors';

@Injectable()
export class WatchlistFacade {
  /**
   * Combine pieces of state using createSelector,
   * and expose them as observables through the facade.
   */
  loaded$ = this.store.pipe(select(WatchlistSelectors.getWatchlistLoaded));
  allWatchlist$ = this.store.pipe(select(WatchlistSelectors.getAllWatchlist));
  selectedWatchlist$ = this.store.pipe(select(WatchlistSelectors.getSelected));

  constructor(private readonly store: Store) {}

  /**
   * Use the initialization action to perform one
   * or more tasks in your Effects.
   */
  init() {
    this.store.dispatch(WatchlistActions.init());
  }
}
