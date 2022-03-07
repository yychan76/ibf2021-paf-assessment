import { createFeatureSelector } from '@ngrx/store';
import * as auth from './reducers/auth.reducers';
import * as nav from './reducers/nav.reducers'


// export interface AppState {
//   authState: auth.State;
// }

export const reducers = {
  auth: auth.authReducer,
  nav: nav.navReducer
};

// export const selectAuthState = createFeatureSelector<AppState>('auth');
