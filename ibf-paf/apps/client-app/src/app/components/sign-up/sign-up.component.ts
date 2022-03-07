import { Component, OnInit } from '@angular/core';
import { User } from '../../../../../../libs/core-state/src/lib/store/models/user';

@Component({
  selector: 'ibf-paf-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss']
})
export class SignUpComponent implements OnInit {
  user: User = new User();

  constructor() { }

  ngOnInit(): void {
  }

  onSubmit(): void {
    console.log(this.user);
  }

}
