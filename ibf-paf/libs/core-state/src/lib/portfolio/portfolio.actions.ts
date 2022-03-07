import { createAction, props } from '@ngrx/store';
import { PortfolioEntity } from './portfolio.models';

export const init = createAction('[Portfolio Page] Init');

export const loadPortfolioSuccess = createAction(
  '[Portfolio/API] Load Portfolio Success',
  props<{ portfolio: PortfolioEntity[] }>()
);

export const loadPortfolioFailure = createAction(
  '[Portfolio/API] Load Portfolio Failure',
  props<{ error: any }>()
);
