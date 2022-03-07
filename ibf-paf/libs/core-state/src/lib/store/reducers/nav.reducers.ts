import { createReducer, on } from "@ngrx/store";
import { User } from "../models/user";
import * as navActions from '../actions/nav.actions';

export interface State {
  wId: string | null;
  pId: string | null;
  psId: string | null;
}



// TODO set up properly later
const user = new User();
user.uId = "15";
user.email = "user@example.com";
user.password = "test";
user.displayName = "Demo User";

export const initialState: State = {
  wId: null,
  pId: null,
  psId: null
}

export const navReducer = createReducer(
  initialState,
  on(navActions.selectWatchlist, (state, {wId}): State => ({...state, wId: wId}) ),
  on(navActions.selectPortfolio, (state, {pId}): State => ({...state, pId: pId}) ),
  on(navActions.selectPortfolioStock, (state, {psId}): State => ({...state, psId: psId})),
)
