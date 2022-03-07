import { Injectable } from '@angular/core';
import { createEffect, Actions, ofType } from '@ngrx/effects';
import { fetch } from '@nrwl/angular';

import * as PortfolioActions from './portfolio.actions';
import * as PortfolioFeature from './portfolio.reducer';

@Injectable()
export class PortfolioEffects {
  init$ = createEffect(() =>
    this.actions$.pipe(
      ofType(PortfolioActions.init),
      fetch({
        run: (action) => {
          // Your custom service 'load' logic goes here. For now just return a success action...
          return PortfolioActions.loadPortfolioSuccess({ portfolio: [] });
        },
        onError: (action, error) => {
          console.error('Error', error);
          return PortfolioActions.loadPortfolioFailure({ error });
        },
      })
    )
  );

  constructor(private readonly actions$: Actions) {}
}
