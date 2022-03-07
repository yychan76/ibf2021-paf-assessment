import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { login } from './login-page.actions';

@Component({
  selector: 'ibf-paf-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent   {

  constructor(private store: Store) { }



  onSubmit(email: string, password: string) {
    this.store.dispatch(login({ email: email, password: password }));
  }

}
