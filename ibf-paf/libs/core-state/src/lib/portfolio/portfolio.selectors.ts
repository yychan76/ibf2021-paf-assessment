import { createFeatureSelector, createSelector } from '@ngrx/store';
import {
  PORTFOLIO_FEATURE_KEY,
  State,
  portfolioAdapter,
} from './portfolio.reducer';

// Lookup the 'Portfolio' feature state managed by NgRx
export const getPortfolioState = createFeatureSelector<State>(
  PORTFOLIO_FEATURE_KEY
);

const { selectAll, selectEntities } = portfolioAdapter.getSelectors();

export const getPortfolioLoaded = createSelector(
  getPortfolioState,
  (state: State) => state.loaded
);

export const getPortfolioError = createSelector(
  getPortfolioState,
  (state: State) => state.error
);

export const getAllPortfolio = createSelector(
  getPortfolioState,
  (state: State) => selectAll(state)
);

export const getPortfolioEntities = createSelector(
  getPortfolioState,
  (state: State) => selectEntities(state)
);

export const getSelectedId = createSelector(
  getPortfolioState,
  (state: State) => state.selectedId
);

export const getSelected = createSelector(
  getPortfolioEntities,
  getSelectedId,
  (entities, selectedId) => (selectedId ? entities[selectedId] : undefined)
);
