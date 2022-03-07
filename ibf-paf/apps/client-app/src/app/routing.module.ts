import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LandingComponent } from './components/landing/landing.component';
import { LogInComponent } from './components/log-in/log-in.component';
import { PortfolioDetailComponent } from './components/portfolio/portfolio-detail/portfolio-detail.component';
import { TransactionComponent } from './components/portfolio/portfolio-detail/transaction/transaction.component';
import { PortfolioComponent } from './components/portfolio/portfolio.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { SymbolSearchComponent } from './components/symbol-search/symbol-search.component';
import { WatchlistDetailComponent } from './components/watchlist/watchlist-detail/watchlist-detail.component';
import { WatchlistComponent } from './components/watchlist/watchlist.component';
import { HomeComponent } from './home/home.component';

const routes: Routes = [
  // { path: '', component: LandingComponent },
  { path: '', component: HomeComponent },
  { path: 'log-in', component: LogInComponent },
  { path: 'sign-up', component: SignUpComponent },
  { path: 'home', component: HomeComponent },
  { path: 'search', component: SymbolSearchComponent },
  { path: 'watchlist', component: WatchlistComponent },
  { path: 'watchlist/:wId', component: WatchlistDetailComponent },
  { path: 'portfolio', component: PortfolioComponent },
  { path: 'portfolio/:pId', component: PortfolioDetailComponent },
  { path: 'portfolio/:pId/stock/:sId', component: TransactionComponent },

  { path: '**', redirectTo: '/', pathMatch: 'full' },
];

@NgModule({
  declarations: [],
  imports: [CommonModule, RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule],
})
export class RoutingModule {}
