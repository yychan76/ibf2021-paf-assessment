import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { User } from '../../../../../../libs/core-state/src/lib/store/models/user';
import { login } from '@ibf-paf/core-state'

@Component({
  selector: 'ibf-paf-log-in',
  templateUrl: './log-in.component.html',
  styleUrls: ['./log-in.component.scss']
})
export class LogInComponent implements OnInit {
  user: User = new User();

  constructor(private store: Store) { }

  ngOnInit(): void {
  }

  onSubmit(): void {
    console.log(this.user);
    const email = this.user.email || "";
    const password = this.user.password || "";
    this.store.dispatch(login({ email: email, password: password}));
  }

}
