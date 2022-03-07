import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import {
  AppState,
  selectAuthState,
  selectNavState,
  selectWatchlist,
} from '@ibf-paf/core-state';
import { WatchlistService } from '@ibf-paf/core-data';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'ibf-paf-watchlist',
  templateUrl: './watchlist.component.html',
  styleUrls: ['./watchlist.component.scss'],
})
export class WatchlistComponent implements OnInit {
  form!: FormGroup;
  auth$!: Observable<AppState>;
  nav$!: Observable<AppState>;
  watchlists$!: Observable<any>;

  constructor(
    private store: Store,
    private watchlistService: WatchlistService,
    private fb: FormBuilder
  ) {
    this.auth$ = this.store.select(selectAuthState);
    this.nav$ = this.store.select(selectNavState);
  }

  ngOnInit(): void {
    this.fetchAllWatchlists();
    this.initForm();
  }

  fetchAllWatchlists() {
    this.watchlists$ = this.watchlistService.all();
  }

  initForm() {
    this.form = this.fb.group({
      watchlistname: [''],
    });
  }

  onSubmit() {
    const watchlistname = this.form.value.watchlistname;
    const watchlist = { wName: watchlistname };
    console.log(watchlist);
    this.watchlistService.create(watchlist).subscribe((result) => {
      console.log(result);
      this.form.reset();
      this.fetchAllWatchlists();
    });
  }

  onSelect(wId: string): void {
    this.store.dispatch(selectWatchlist({ wId }));
  }
}
