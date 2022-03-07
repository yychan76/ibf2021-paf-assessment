import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ShareCounter } from '@ibf-paf/api-interfaces';
import { WatchlistService, WatchlistStockService } from '@ibf-paf/core-data';
import { AppState, selectAuthState, selectNavState, selectWatchlist } from '@ibf-paf/core-state';
import { Store } from '@ngrx/store';
import { Observable, Subscription } from 'rxjs';

@Component({
  selector: 'ibf-paf-watchlist-detail',
  templateUrl: './watchlist-detail.component.html',
  styleUrls: ['./watchlist-detail.component.scss'],
})
export class WatchlistDetailComponent implements OnInit, OnDestroy {
  auth$!: Observable<AppState>;
  nav$!: Observable<AppState>;
  watchlist$!: Observable<any>;
  stocks$!: Observable<any>;
  wId!: string;
  createSub!: Subscription;
  deleteSub!: Subscription;


  constructor(
    private store: Store,
    private watchlistService: WatchlistService,
    private watchlistStockService: WatchlistStockService,
    private activatedRoute: ActivatedRoute,
    private router: Router
    ) {
      this.auth$ = this.store.select(selectAuthState);
      this.nav$ = this.store.select(selectNavState);
  }

  ngOnInit(): void {
    this.wId = this.activatedRoute.snapshot.params['wId'];
    this.watchlist$ = this.watchlistService.find(this.wId);
    this.fetchWatclistStocks();
  }

  ngOnDestroy(): void {
    this.createSub?.unsubscribe();
    this.deleteSub?.unsubscribe();
  }

  fetchWatclistStocks() {
    this.stocks$ = this.watchlistStockService.all();
  }

  addSymbolToWatchlist(symbol: string): void {
    console.log('addSymbolToWatchlist: ', symbol);
    this.createSub = this.watchlistStockService
      .create({ symbol: symbol })
      .subscribe((result) => {
        console.log(result);
        this.fetchWatclistStocks();
      });
  }

  removeSymbolFromWatchlist(shareCounter: ShareCounter): void {
    console.log('removeSymbolFromWatchlist: ', shareCounter);
    this.deleteSub = this.watchlistStockService
      .delete(shareCounter)
      .subscribe((result) => {
        console.log(result);
        this.fetchWatclistStocks();
      });
  }

  getChangeValue(curr: number, prev: number): number {
    return curr - prev;
  }

  getPriceClass(change: number) {
    return { gain: change > 0, loss: change < 0 };
  }

  onDelete() {
    this.watchlistService.delete({wId: this.wId}).subscribe((result) => {
      console.log(result);
      this.goBack();
    })
  }

  goBack() {
    this.router.navigate(['/watchlist'])
  }
}
