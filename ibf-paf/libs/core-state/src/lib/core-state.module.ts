import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CoreDataModule } from '@ibf-paf/core-data';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import * as fromWatchlist from './watchlist/watchlist.reducer';
import { WatchlistEffects } from './watchlist/watchlist.effects';
import { WatchlistFacade } from './watchlist/watchlist.facade';
import * as fromPortfolio from './portfolio/portfolio.reducer';
import { PortfolioEffects } from './portfolio/portfolio.effects';
import { PortfolioFacade } from './portfolio/portfolio.facade';


@NgModule({
  imports: [
    CommonModule,
    CoreDataModule,
    StoreModule.forFeature(
      fromWatchlist.WATCHLIST_FEATURE_KEY,
      fromWatchlist.reducer
    ),
    EffectsModule.forFeature([WatchlistEffects]),
    StoreModule.forFeature(
      fromPortfolio.PORTFOLIO_FEATURE_KEY,
      fromPortfolio.reducer
    ),
    EffectsModule.forFeature([PortfolioEffects]),
  ],
  providers: [WatchlistFacade, PortfolioFacade],
})
export class CoreStateModule {}
