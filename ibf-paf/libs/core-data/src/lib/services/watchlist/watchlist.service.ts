import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Watchlist } from '@ibf-paf/api-interfaces';
import { environment } from '@env/environment'
import { AppState , selectAuthState} from '@ibf-paf/core-state';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class WatchlistService {
  auth$!: Observable<AppState>;
  model = 'watchlists';

  constructor(private http: HttpClient, private store: Store) {
    this.auth$ = this.store.select(selectAuthState);
  }

  all() {
    return this.http.get<Watchlist[]>(this.getUrl());
  }

  find(id: string) {
    return this.http.get<Watchlist>(this.getUrlWithId(id));
  }

  create(watchlist: Watchlist) {
    return this.http.post(this.getUrl(), watchlist);
  }

  update(watchlist: Watchlist) {
    return this.http.put(this.getUrlWithId(watchlist.wId), watchlist);
  }

  delete(watchlist: Watchlist) {
    return this.http.delete(this.getUrlWithId(watchlist.wId));
  }

  private getUrl() {
    return `${environment.apiEndpoint}users/15/${this.model}`;
  }

  private getUrlWithId(id: string): string {
    return `${this.getUrl()}/${id}`;
  }
}
