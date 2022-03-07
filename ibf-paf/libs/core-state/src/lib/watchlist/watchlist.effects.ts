import { Injectable } from '@angular/core';
import { createEffect, Actions, ofType } from '@ngrx/effects';
import { fetch } from '@nrwl/angular';

import * as WatchlistActions from './watchlist.actions';
import * as WatchlistFeature from './watchlist.reducer';

@Injectable()
export class WatchlistEffects {
  init$ = createEffect(() =>
    this.actions$.pipe(
      ofType(WatchlistActions.init),
      fetch({
        run: (action) => {
          // Your custom service 'load' logic goes here. For now just return a success action...
          return WatchlistActions.loadWatchlistSuccess({ watchlist: [] });
        },
        onError: (action, error) => {
          console.error('Error', error);
          return WatchlistActions.loadWatchlistFailure({ error });
        },
      })
    )
  );

  constructor(private readonly actions$: Actions) {}
}
