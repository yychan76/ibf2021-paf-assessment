import { createAction, props } from '@ngrx/store';

export const selectWatchlist = createAction(
  '[Navigation] Select Watchlist',
  props<{ wId: string }>()
);

export const selectPortfolio = createAction(
  '[Navigation] Select Portfolio',
  props<{ pId: string }>()
);

export const selectPortfolioStock = createAction(
  '[Navigation] Select Portfolio',
  props<{ psId: string }>()
);
