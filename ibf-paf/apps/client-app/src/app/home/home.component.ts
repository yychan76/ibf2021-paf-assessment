import { Component, OnInit } from '@angular/core';
import { User } from '@ibf-paf/api-interfaces';
import { UserService } from '@ibf-paf/core-data';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { AppState, selectAuthState, selectNavState } from '@ibf-paf/core-state';

@Component({
  selector: 'ibf-paf-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  users$!: Observable<User[]>;
  auth$!: Observable<AppState>;
  nav$!: Observable<AppState>;

  constructor(private userService: UserService, private store: Store) {
    this.auth$ = this.store.select(selectAuthState);
    this.nav$ = this.store.select(selectNavState);
  }

  ngOnInit(): void {
    // this.users$ = this.userService.all();
  }
}
