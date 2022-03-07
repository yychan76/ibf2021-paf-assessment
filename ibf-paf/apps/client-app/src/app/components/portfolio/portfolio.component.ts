import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { PortfolioService } from '@ibf-paf/core-data';
import { AppState, selectAuthState, selectNavState, selectPortfolio } from '@ibf-paf/core-state';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';

@Component({
  selector: 'ibf-paf-portfolio',
  templateUrl: './portfolio.component.html',
  styleUrls: ['./portfolio.component.scss']
})
export class PortfolioComponent implements OnInit {

  form!: FormGroup;
  auth$!: Observable<AppState>;
  nav$!: Observable<AppState>;
  portfolio$!: Observable<any>;

  constructor(
    private store: Store,
    private portfolioService: PortfolioService,
    private fb: FormBuilder
  ) {
    this.auth$ = this.store.select(selectAuthState);
    this.nav$ = this.store.select(selectNavState);
  }

  ngOnInit(): void {
    this.fetchAllPortfolios();
    this.initForm();
  }

  fetchAllPortfolios() {
    this.portfolio$ = this.portfolioService.all();
  }

  initForm() {
    this.form = this.fb.group({
      portfolioname: [''],
    });
  }

  onSubmit() {
    const portfolioname = this.form.value.portfolioname;
    const portfolio = { pName: portfolioname };
    console.log(portfolio);
    this.portfolioService.create(portfolio).subscribe((result) => {
      console.log(result);
      this.form.reset();
      this.fetchAllPortfolios();
    });
  }

  onSelect(pId: string): void {
    this.store.dispatch(selectPortfolio({ pId }));
  }

}
