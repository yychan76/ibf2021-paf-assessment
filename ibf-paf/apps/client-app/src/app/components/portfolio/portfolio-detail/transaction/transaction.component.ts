import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PortfolioService, PortfolioStockService } from '@ibf-paf/core-data';
import { AppState, selectAuthState, selectNavState } from '@ibf-paf/core-state';
import { Store } from '@ngrx/store';
import { Observable, Subscription } from 'rxjs';

@Component({
  selector: 'ibf-paf-transaction',
  templateUrl: './transaction.component.html',
  styleUrls: ['./transaction.component.scss']
})
export class TransactionComponent implements OnInit {
  form!: FormGroup;
  auth$!: Observable<AppState>;
  nav$!: Observable<AppState>;
  stock$!: Observable<any>;
  pId!: string;
  psId!: string;
  createSub!: Subscription;
  deleteSub!: Subscription;

  constructor(
    private store: Store,
    private portfolioStockService: PortfolioStockService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {
    this.auth$ = this.store.select(selectAuthState);
    this.nav$ = this.store.select(selectNavState);
  }

  ngOnInit(): void {
    this.psId = this.activatedRoute.snapshot.params['psId'];
    this.pId = this.activatedRoute.snapshot.params['pId'];
    this.stock$ = this.portfolioStockService.find(this.psId);
  }

}
