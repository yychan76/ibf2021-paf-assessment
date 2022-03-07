import { Action, createReducer, on } from '@ngrx/store';
import * as LoginPageActions from './login-page.actions';

export interface LoginState {
  authenticated: boolean;
}

export const initialState: LoginState = {
  authenticated: false,
};

export const loginReducer = createReducer(
  initialState,
  on(
    LoginPageActions.loginSuccess,
    (state): LoginState => ({ ...state, authenticated: true })
  ),
  on(
    LoginPageActions.logout,
    (state): LoginState => ({ ...state, authenticated: false })
  )
);
