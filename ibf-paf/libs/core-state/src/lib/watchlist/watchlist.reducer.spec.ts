import { Action } from '@ngrx/store';

import * as WatchlistActions from './watchlist.actions';
import { WatchlistEntity } from './watchlist.models';
import { State, initialState, reducer } from './watchlist.reducer';

describe('Watchlist Reducer', () => {
  const createWatchlistEntity = (id: string, name = ''): WatchlistEntity => ({
    id,
    name: name || `name-${id}`,
  });

  describe('valid Watchlist actions', () => {
    it('loadWatchlistSuccess should return the list of known Watchlist', () => {
      const watchlist = [
        createWatchlistEntity('PRODUCT-AAA'),
        createWatchlistEntity('PRODUCT-zzz'),
      ];
      const action = WatchlistActions.loadWatchlistSuccess({ watchlist });

      const result: State = reducer(initialState, action);

      expect(result.loaded).toBe(true);
      expect(result.ids.length).toBe(2);
    });
  });

  describe('unknown action', () => {
    it('should return the previous state', () => {
      const action = {} as Action;

      const result = reducer(initialState, action);

      expect(result).toBe(initialState);
    });
  });
});
