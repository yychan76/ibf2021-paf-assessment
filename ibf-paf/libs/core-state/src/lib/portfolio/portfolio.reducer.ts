import { EntityState, EntityAdapter, createEntityAdapter } from '@ngrx/entity';
import { createReducer, on, Action } from '@ngrx/store';

import * as PortfolioActions from './portfolio.actions';
import { PortfolioEntity } from './portfolio.models';

export const PORTFOLIO_FEATURE_KEY = 'portfolio';

export interface State extends EntityState<PortfolioEntity> {
  selectedId?: string | number; // which Portfolio record has been selected
  loaded: boolean; // has the Portfolio list been loaded
  error?: string | null; // last known error (if any)
}

export interface PortfolioPartialState {
  readonly [PORTFOLIO_FEATURE_KEY]: State;
}

export const portfolioAdapter: EntityAdapter<PortfolioEntity> =
  createEntityAdapter<PortfolioEntity>();

export const initialState: State = portfolioAdapter.getInitialState({
  // set initial required properties
  loaded: false,
});

const portfolioReducer = createReducer(
  initialState,
  on(PortfolioActions.init, (state) => ({
    ...state,
    loaded: false,
    error: null,
  })),
  on(PortfolioActions.loadPortfolioSuccess, (state, { portfolio }) =>
    portfolioAdapter.setAll(portfolio, { ...state, loaded: true })
  ),
  on(PortfolioActions.loadPortfolioFailure, (state, { error }) => ({
    ...state,
    error,
  }))
);

export function reducer(state: State | undefined, action: Action) {
  return portfolioReducer(state, action);
}
