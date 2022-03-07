import { createAction, props } from "@ngrx/store";

export const login = createAction(
  '[Login Page] Login',
  props<{ email: string; password: string }>()
);

export const loginSuccess = createAction(
  '[Auth API] Login Success',
  props<{payload: any}>()
)

export const logout = createAction(
  '[Login Page] Logout'
);
