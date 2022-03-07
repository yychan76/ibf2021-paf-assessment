import { createReducer, on } from '@ngrx/store';
import { User } from '../models/user';
import * as authActions from '../actions/auth.actions';

export interface State {
  // is user Authenticated?
  isAuthenticated: boolean;
  // if authenticated, there should be a user object
  user: User | null;
  errorMessage: string | null;
}

// export const initialState: State = {
//   isAuthenticated: false,
//   user: null,
//   errorMessage: null
// }

// TODO set up properly later
const user = new User();
user.uId = '1';
user.email = 'user@example.com';
user.password = 'test';
user.displayName = 'Demo User';
user.avatarUrl =
  'hhttps://i0.wp.com/www.cssscript.com/wp-content/uploads/2020/12/Customizable-SVG-Avatar-Generator-In-JavaScript-Avataaars.js.png?fit=438%2C408&ssl=1';

export const initialState: State = {
  isAuthenticated: false,
  user: user,
  errorMessage: null,
};

export const authReducer = createReducer(
  initialState,
  on(authActions.login, (state) => ({ ...state })),
  on(authActions.loginSuccess, (state) => ({
    ...state,
    isAuthenticated: true,
    user: user,
  })),
  on(authActions.logout, (state) => ({
    ...state,
    isAuthenticated: false,
    user: user,
  }))
);
