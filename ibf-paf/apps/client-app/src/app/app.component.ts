import { Component } from '@angular/core';
import { UserService } from '@ibf-paf/core-data';
import { AppState, selectAuthState, selectNavState } from '@ibf-paf/core-state';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';

@Component({
  selector: 'ibf-paf-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'stock-app';
  authState: any;
  auth$!: Observable<AppState>;
  nav$!: Observable<AppState>;

  constructor(private userService: UserService, private store: Store) {
    this.auth$ = this.store.select(selectAuthState);
    this.nav$ = this.store.select(selectNavState);
    this.auth$.subscribe((state) => {
      this.authState = {...state};
    });
  }
}
