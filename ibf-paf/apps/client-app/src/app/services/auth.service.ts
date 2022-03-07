import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@env/environment';
import { Observable } from 'rxjs';
import { User } from '../../../../../libs/core-state/src/lib/store/models/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  getToken(): string {
    return localStorage.getItem('token') || "";
  }

  logIn(email: string, password: string): Observable<any> {
    const url = `${environment.apiEndpoint}/login`;
    return this.http.post<User>(url, {email, password});
  }

  signUp(email: string, password: string): Observable<User> {
    const url = `${environment.apiEndpoint}/register`;
    return this.http.post<User>(url, {email, password});
  }
}
