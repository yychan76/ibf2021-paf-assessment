import { WatchlistEntity } from './watchlist.models';
import {
  watchlistAdapter,
  WatchlistPartialState,
  initialState,
} from './watchlist.reducer';
import * as WatchlistSelectors from './watchlist.selectors';

describe('Watchlist Selectors', () => {
  const ERROR_MSG = 'No Error Available';
  const getWatchlistId = (it: WatchlistEntity) => it.id;
  const createWatchlistEntity = (id: string, name = '') =>
    ({
      id,
      name: name || `name-${id}`,
    } as WatchlistEntity);

  let state: WatchlistPartialState;

  beforeEach(() => {
    state = {
      watchlist: watchlistAdapter.setAll(
        [
          createWatchlistEntity('PRODUCT-AAA'),
          createWatchlistEntity('PRODUCT-BBB'),
          createWatchlistEntity('PRODUCT-CCC'),
        ],
        {
          ...initialState,
          selectedId: 'PRODUCT-BBB',
          error: ERROR_MSG,
          loaded: true,
        }
      ),
    };
  });

  describe('Watchlist Selectors', () => {
    it('getAllWatchlist() should return the list of Watchlist', () => {
      const results = WatchlistSelectors.getAllWatchlist(state);
      const selId = getWatchlistId(results[1]);

      expect(results.length).toBe(3);
      expect(selId).toBe('PRODUCT-BBB');
    });

    it('getSelected() should return the selected Entity', () => {
      const result = WatchlistSelectors.getSelected(state) as WatchlistEntity;
      const selId = getWatchlistId(result);

      expect(selId).toBe('PRODUCT-BBB');
    });

    it('getWatchlistLoaded() should return the current "loaded" status', () => {
      const result = WatchlistSelectors.getWatchlistLoaded(state);

      expect(result).toBe(true);
    });

    it('getWatchlistError() should return the current "error" state', () => {
      const result = WatchlistSelectors.getWatchlistError(state);

      expect(result).toBe(ERROR_MSG);
    });
  });
});
