import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@env/environment';
import { map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SymbolSearchService {
  constructor(private http: HttpClient) {}

  search(keywords: string): Observable<Symbol[]> {
    let params = new HttpParams();
    params = params.set('keywords', keywords);
    return this.http
      .get<any>(this.getUrl(), { params })
      .pipe(map((response) => response.matches));
  }

  private getUrl() {
    return `${environment.apiEndpoint}stocks/query`;
  }
}
