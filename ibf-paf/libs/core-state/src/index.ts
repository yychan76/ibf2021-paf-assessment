export * from './lib/portfolio/portfolio.facade';
export * from './lib/portfolio/portfolio.models';
import * as fromPortfolioSelector from './lib/portfolio/portfolio.selectors';
import * as fromPortfolioReducer from './lib/portfolio/portfolio.reducer';
import * as fromPortfolioActions from './lib/portfolio/portfolio.actions';
export * from './lib/watchlist/watchlist.facade';
export * from './lib/watchlist/watchlist.models';
import * as fromWatchlistSelector from './lib/watchlist/watchlist.selectors';
import * as fromWatchlistReducer from './lib/watchlist/watchlist.reducer';
import * as fromWatchlistActions from './lib/watchlist/watchlist.actions';
import { ActionReducerMap, createFeatureSelector } from '@ngrx/store';
export * from './lib/core-state.module';
export { login, loginSuccess, logout } from './lib/store/actions/auth.actions';
export {
  selectWatchlist,
  selectPortfolio,
  selectPortfolioStock,
} from './lib/store/actions/nav.actions';

import * as auth from './lib/store/reducers/auth.reducers';
import * as nav from './lib/store/reducers/nav.reducers';

// update shape of entire app state
export interface AppState {
  portfolios: fromPortfolioReducer.State;
  watchlist: fromWatchlistReducer.State;
  authState: auth.State;
  navState: nav.State;
}

export const reducers = {
  auth: auth.authReducer,
  nav: nav.navReducer
};

export const selectAuthState = createFeatureSelector<AppState>('auth');
export const selectNavState = createFeatureSelector<AppState>('nav');

// add in feature reducers to combined reducer
// export const reducers: ActionReducerMap<AppState> = {
//   portfolios: fromPortfolioReducer.reducer,
//   watchlist: fromWatchlistReducer.reducer,
//   auth: auth.authReducer,
// };
