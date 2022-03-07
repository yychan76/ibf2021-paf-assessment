import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Stock } from '@ibf-paf/api-interfaces';
import { PortfolioService, PortfolioStockService } from '@ibf-paf/core-data';
import { AppState, selectAuthState, selectNavState } from '@ibf-paf/core-state';
import { Store } from '@ngrx/store';
import { Observable, Subscription } from 'rxjs';

@Component({
  selector: 'ibf-paf-portfolio-detail',
  templateUrl: './portfolio-detail.component.html',
  styleUrls: ['./portfolio-detail.component.scss'],
})
export class PortfolioDetailComponent implements OnInit, OnDestroy {
  auth$!: Observable<AppState>;
  nav$!: Observable<AppState>;
  portfolio$!: Observable<any>;
  stocks$!: Observable<any>;
  pId!: string;
  createSub!: Subscription;
  deleteSub!: Subscription;

  constructor(
    private store: Store,
    private portfolioService: PortfolioService,
    private portfolioStockService: PortfolioStockService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {
    this.auth$ = this.store.select(selectAuthState);
    this.nav$ = this.store.select(selectNavState);
  }

  ngOnInit(): void {
    this.pId = this.activatedRoute.snapshot.params['pId'];
    this.portfolio$ = this.portfolioService.find(this.pId);
    this.fetchPortfolioStocks();
  }

  ngOnDestroy(): void {
    this.createSub?.unsubscribe();
    this.deleteSub?.unsubscribe();
  }

  fetchPortfolioStocks() {
    this.stocks$ = this.portfolioStockService.all();
  }

  addSymbolToPortfolio(symbol: string): void {
    console.log('addSymbolToPortfolio: ', symbol);
    this.createSub = this.portfolioStockService
      .create({ symbol: symbol })
      .subscribe((result) => {
        console.log(result);
        this.fetchPortfolioStocks();
      });
  }

  removeSymbolFromPortfolio(stock: Stock): void {
    console.log('removeSymbolFromPortfolio: ', stock);
    this.deleteSub = this.portfolioStockService
      .delete(stock)
      .subscribe((result) => {
        console.log(result);
        this.fetchPortfolioStocks();
      });
  }

  getChangeValue(curr: number, prev: number): number {
    return curr - prev;
  }

  getPriceClass(change: number) {
    return { gain: change > 0, loss: change < 0 };
  }

  onDelete() {
    this.portfolioService.delete({ pId: this.pId }).subscribe((result) => {
      console.log(result);
      this.goBack();
    });
  }

  goBack() {
    this.router.navigate(['/portfolio']);
  }
}
