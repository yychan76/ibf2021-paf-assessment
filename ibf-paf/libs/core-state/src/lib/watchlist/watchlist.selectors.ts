import { createFeatureSelector, createSelector } from '@ngrx/store';
import {
  WATCHLIST_FEATURE_KEY,
  State,
  watchlistAdapter,
} from './watchlist.reducer';

// Lookup the 'Watchlist' feature state managed by NgRx
export const getWatchlistState = createFeatureSelector<State>(
  WATCHLIST_FEATURE_KEY
);

const { selectAll, selectEntities } = watchlistAdapter.getSelectors();

export const getWatchlistLoaded = createSelector(
  getWatchlistState,
  (state: State) => state.loaded
);

export const getWatchlistError = createSelector(
  getWatchlistState,
  (state: State) => state.error
);

export const getAllWatchlist = createSelector(
  getWatchlistState,
  (state: State) => selectAll(state)
);

export const getWatchlistEntities = createSelector(
  getWatchlistState,
  (state: State) => selectEntities(state)
);

export const getSelectedId = createSelector(
  getWatchlistState,
  (state: State) => state.selectedId
);

export const getSelected = createSelector(
  getWatchlistEntities,
  getSelectedId,
  (entities, selectedId) => (selectedId ? entities[selectedId] : undefined)
);
