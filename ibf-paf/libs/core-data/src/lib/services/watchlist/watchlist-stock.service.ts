import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ShareCounter } from '@ibf-paf/api-interfaces';
import { environment } from '@env/environment';
import { AppState, selectAuthState, selectNavState } from '@ibf-paf/core-state';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root',
})
export class WatchlistStockService {
  auth$!: Observable<AppState>;
  nav$!: Observable<AppState>;
  uId!: string;
  authState: any;
  wId!: string;
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
      this.wId = this.state['wId'];
      console.log('WatchlistStockService', this.uId, this.wId, state);
    });
  }

  all() {
    return this.http.get<ShareCounter[]>(this.getUrl());
  }

  find(wsId: string) {
    return this.http.get<ShareCounter>(this.getUrlWithId(wsId));
  }

  create(sharecounter: ShareCounter) {
    return this.http.post(this.getUrl(), sharecounter);
  }

  update(sharecounter: ShareCounter) {
    return this.http.put(
      this.getUrlWithId(sharecounter.wsId || ''),
      sharecounter
    );
  }

  delete(sharecounter: ShareCounter) {
    console.log('Deleting: wsId', sharecounter.wsId);
    console.log(this.getUrlWithId(sharecounter.wsId || ''));
    return this.http.delete(this.getUrlWithId(sharecounter.wsId || ''));
  }

  private getUrl() {
    return `${environment.apiEndpoint}users/${this.uId}/watchlists/${this.wId}/${this.model}`;
  }

  private getUrlWithId(wsId: string): string {
    return `${this.getUrl()}/${wsId}`;
  }
}
