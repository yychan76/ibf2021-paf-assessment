import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { AppState, selectAuthState } from '@ibf-paf/core-state';
import { WatchlistService } from '@ibf-paf/core-data';

@Component({
  selector: 'ibf-paf-watchlist',
  templateUrl: './watchlist.component.html',
  styleUrls: ['./watchlist.component.scss']
})
export class WatchlistComponent implements OnInit {
  auth$!: Observable<AppState>;
  watchlists$!: Observable<any>;

  constructor(private store: Store, private watchlistService: WatchlistService) {
    this.auth$ = this.store.select(selectAuthState);
  }

  ngOnInit(): void {
    this.watchlists$ = this.watchlistService.all();
  }



}
