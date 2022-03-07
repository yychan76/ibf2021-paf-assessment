import { createAction, props } from '@ngrx/store';
import { WatchlistEntity } from './watchlist.models';

export const init = createAction('[Watchlist Page] Init');

export const loadWatchlistSuccess = createAction(
  '[Watchlist/API] Load Watchlist Success',
  props<{ watchlist: WatchlistEntity[] }>()
);

export const loadWatchlistFailure = createAction(
  '[Watchlist/API] Load Watchlist Failure',
  props<{ error: any }>()
);
