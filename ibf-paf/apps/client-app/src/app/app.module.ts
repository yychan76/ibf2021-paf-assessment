import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CoreDataModule } from '@ibf-paf/core-data';
import { CoreStateModule, reducers } from '@ibf-paf/core-state';
import { MaterialModule } from '@ibf-paf/material';
import { StoreModule } from '@ngrx/store';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { NxWelcomeComponent } from './nx-welcome.component';
import { RoutingModule } from './routing.module';
import { LoginPageComponent } from './login-page/login-page.component';
import { LandingComponent } from './components/landing/landing.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { LogInComponent } from './components/log-in/log-in.component';
import { EffectsModule } from '@ngrx/effects';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from './services/auth.service';
import { WatchlistComponent } from './components/watchlist/watchlist.component';
import { WatchlistDetailComponent } from './components/watchlist/watchlist-detail/watchlist-detail.component';
import { PortfolioComponent } from './components/portfolio/portfolio.component';
import { PortfolioListComponent } from './components/portfolio/portfolio-list/portfolio-list.component';
import { PortfolioDetailComponent } from './components/portfolio/portfolio-detail/portfolio-detail.component';
import { TransactionComponent } from './components/portfolio/portfolio-detail/transaction/transaction.component';
import { SymbolSearchComponent } from './components/symbol-search/symbol-search.component';

@NgModule({
  declarations: [
    AppComponent,
    NxWelcomeComponent,
    HomeComponent,
    LoginPageComponent,
    LandingComponent,
    SignUpComponent,
    LogInComponent,
    WatchlistComponent,
    WatchlistDetailComponent,
    PortfolioComponent,
    PortfolioListComponent,
    PortfolioDetailComponent,
    TransactionComponent,
    SymbolSearchComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    StoreModule.forRoot(reducers, {}),
    EffectsModule.forRoot([]),
    CoreDataModule,
    CoreStateModule,
    MaterialModule,
    RoutingModule,
    HttpClientModule,
    FlexLayoutModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [AuthService],
  bootstrap: [AppComponent],
})
export class AppModule {}
