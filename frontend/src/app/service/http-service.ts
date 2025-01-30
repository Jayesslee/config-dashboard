import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpHeaders,
  HttpParams,
  HttpResponse,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { environmentAuth } from '../../environments/environment.auth';
import { UserAuth } from '../model/UserAuth';

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  private apiUrlAuth = environmentAuth.apiUrl;
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  register(userAuth: UserAuth): Observable<HttpResponse<any>> {
    return this.http.post(`${this.apiUrlAuth}/user/register`, userAuth, {
      // fetch complete response including response headers
      observe: 'response',
    });
  }

  login(userAuth: UserAuth): Observable<HttpResponse<any>> {
    return this.http.post(`${this.apiUrlAuth}/auth/login`, userAuth, {
      // fetch complete response including response headers
      observe: 'response',
    });
  }

  get<T>(endpoint: string, params?: any): Observable<T> {
    const options = { params: new HttpParams({ fromObject: params }) };
    return this.http.get<T>(`${this.apiUrl}/${endpoint}`, options);
  }

  post<T>(endpoint: string, body: any, headers?: HttpHeaders): Observable<T> {
    return this.http.post<T>(`${this.apiUrl}/${endpoint}`, body, { headers });
  }

  put<T>(endpoint: string, body: any, headers?: HttpHeaders): Observable<T> {
    return this.http.put<T>(`${this.apiUrl}/${endpoint}`, body, { headers });
  }

  delete<T>(endpoint: string, params?: any): Observable<T> {
    const options = { params: new HttpParams({ fromObject: params }) };
    return this.http.delete<T>(`${this.apiUrl}/${endpoint}`, options);
  }
}
