import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@env/environment';
import { Portfolio } from '@ibf-paf/api-interfaces';
import { AppState, selectAuthState } from '@ibf-paf/core-state';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PortfolioService {

  auth$!: Observable<AppState>;
  model = 'portfolios';
  uId!: string;
  authState: any;

  constructor(private http: HttpClient, private store: Store) {
    this.auth$ = this.store.select(selectAuthState);
    this.auth$.subscribe((state) => {
      this.authState = {...state};
      console.log('this user ', this.authState, this.authState.user.uId);
      this.uId = this.authState.user.uId;
    });
  }

  all() {
    return this.http.get<Portfolio[]>(this.getUrl());
  }

  find(id: string) {
    return this.http.get<Portfolio>(this.getUrlWithId(id));
  }

  create(portfolio: Portfolio) {
    console.log('Creating: portfolio', portfolio);
    console.log(this.getUrl());
    return this.http.post(this.getUrl(), portfolio);
  }

  update(portfolio: Portfolio) {
    return this.http.put(this.getUrlWithId(portfolio.pId || ""), portfolio);
  }

  delete(portfolio: Portfolio) {
    return this.http.delete(this.getUrlWithId(portfolio.pId || ""));
  }

  private getUrl() {
    return `${environment.apiEndpoint}users/${this.uId}/${this.model}`;
  }

  private getUrlWithId(id: string): string {
    return `${this.getUrl()}/${id}`;
  }
}
