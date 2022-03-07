import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@env/environment';
import { User } from '@ibf-paf/api-interfaces';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  model = 'users';

  constructor(private http: HttpClient) {}

  all() {
    console.log(this.getUrl());
    return this.http.get<User[]>(this.getUrl());
  }

  find(id: string) {
    return this.http.get<User>(this.getUrlWithId(id));
  }

  create(user: User) {
    return this.http.post(this.getUrl(), user);
  }

  update(user: User) {
    return this.http.put(this.getUrlWithId(user.uId), user);
  }

  delete(user: User) {
    return this.http.delete(this.getUrlWithId(user.uId));
  }

  private getUrl() {
    return `${environment.apiEndpoint}${this.model}`;
  }

  private getUrlWithId(id: string): string {
    return `${this.getUrl()}/${id}`;
  }
}
