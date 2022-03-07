import { EntityState, EntityAdapter, createEntityAdapter } from '@ngrx/entity';
import { createReducer, on, Action } from '@ngrx/store';

import * as WatchlistActions from './watchlist.actions';
import { WatchlistEntity } from './watchlist.models';

export const WATCHLIST_FEATURE_KEY = 'watchlist';

export interface State extends EntityState<WatchlistEntity> {
  selectedId?: string | number; // which Watchlist record has been selected
  loaded: boolean; // has the Watchlist list been loaded
  error?: string | null; // last known error (if any)
}

export interface WatchlistPartialState {
  readonly [WATCHLIST_FEATURE_KEY]: State;
}

export const watchlistAdapter: EntityAdapter<WatchlistEntity> =
  createEntityAdapter<WatchlistEntity>();

export const initialState: State = watchlistAdapter.getInitialState({
  // set initial required properties
  loaded: false,
});

const watchlistReducer = createReducer(
  initialState,
  on(WatchlistActions.init, (state) => ({
    ...state,
    loaded: false,
    error: null,
  })),
  on(WatchlistActions.loadWatchlistSuccess, (state, { watchlist }) =>
    watchlistAdapter.setAll(watchlist, { ...state, loaded: true })
  ),
  on(WatchlistActions.loadWatchlistFailure, (state, { error }) => ({
    ...state,
    error,
  }))
);

export function reducer(state: State | undefined, action: Action) {
  return watchlistReducer(state, action);
}
