import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Stock } from '@ibf-paf/api-interfaces';
import { environment } from '@env/environment';
import { AppState, selectAuthState, selectNavState } from '@ibf-paf/core-state';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root',
})
export class PortfolioStockService {
  auth$!: Observable<AppState>;
  nav$!: Observable<AppState>;
  uId!: string;
  authState: any;
  pId!: string;
  state: any;
  model = 'stocks';

  constructor(private http: HttpClient, private store: Store) {
    this.auth$ = this.store.select(selectAuthState);
    this.nav$ = this.store.select(selectNavState);
    this.auth$.subscribe((state) => {
      this.authState = {...state};
      console.log('this user ', this.authState, this.authState.user.uId);
      this.uId = this.authState.user.uId;
    });
    this.nav$.subscribe((state) => {
      this.state = { ...state };
      this.pId = this.state['pId'];
      console.log('PortfolioStockService', this.uId, this.pId, state);
    });
  }

  all() {
    return this.http.get<Stock[]>(this.getUrl());
  }

  find(psId: string) {
    return this.http.get<Stock>(this.getUrlWithId(psId));
  }

  create(stock: Stock) {
    return this.http.post(this.getUrl(), stock);
  }

  update(stock: Stock) {
    return this.http.put(
      this.getUrlWithId(stock.psId || ''),
      stock
    );
  }

  delete(stock: Stock) {
    console.log('Deleting: psId', stock.psId);
    console.log(this.getUrlWithId(stock.psId || ''));
    return this.http.delete(this.getUrlWithId(stock.psId || ''));
  }

  private getUrl() {
    return `${environment.apiEndpoint}users/${this.uId}/portfolios/${this.pId}/${this.model}`;
  }

  private getUrlWithId(psId: string): string {
    return `${this.getUrl()}/${psId}`;
  }
}
