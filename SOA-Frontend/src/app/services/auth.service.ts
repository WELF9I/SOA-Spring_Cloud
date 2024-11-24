import { HttpClient } from '@angular/common/http';
import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../model/user.model';
import { JwtHelperService } from '@auth0/angular-jwt';
import { catchError, Observable, of, switchMap } from 'rxjs';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private helper = new JwtHelperService();

  apiURL: string = 'http://localhost:8888/users';
  token!: string;

  public loggedUser!: string;
  public isloggedIn: Boolean = false;
  public roles!: string[];
  public regitredUser: User = new User();

  constructor(
    private router: Router,
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object
  ) { }

  login(user: User): Observable<any> {
    return this.http.post<User>(this.apiURL + '/login', user, { observe: 'response' });
  }
 
  saveToken(jwt: string | null) {
    if (!jwt) {
      console.error('Invalid JWT: Token is null or undefined.');
      return;
    }
    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem('jwt', jwt);
    }
    this.token = jwt;
    this.isloggedIn = true; 
    this.decodeJWT();
  }
  

  getToken(): string {
    if (isPlatformBrowser(this.platformId) && !this.token) {
      this.token = localStorage.getItem('jwt') || '';
    }
    return this.token;
  }

  decodeJWT() {   
    if (this.token == undefined)
      return;
    const decodedToken = this.helper.decodeToken(this.token);
    this.roles = decodedToken.roles;
    this.loggedUser = decodedToken.sub;
  }

  isAdmin(): Boolean {
    if (!this.roles)
      return false;
    return (this.roles.indexOf('ADMIN') > -1);
  }  

  logout() {
    this.loggedUser = undefined!;
    this.roles = undefined!;
    this.token = undefined!;
    this.isloggedIn = false;
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem('jwt');
    }
    this.router.navigate(['/login']);
  }

  loadToken() {
    if (isPlatformBrowser(this.platformId)) {
      this.token = localStorage.getItem('jwt') || '';
    }
    this.decodeJWT();
  }

  isTokenExpired(): Boolean {
    return this.helper.isTokenExpired(this.token);   
  }

  isTokenAboutToExpire(): Boolean {
    if (!this.token) return true;
    const expirationDate = this.helper.getTokenExpirationDate(this.token);
    if (!expirationDate) return true;
    return (expirationDate.getTime() - Date.now()) / 1000 < 300; // Less than 5 minutes
  }

  refreshToken(): Observable<boolean> {
    if (isPlatformBrowser(this.platformId)) {
      const refreshToken = localStorage.getItem('refreshToken');
      if (!refreshToken) {
        return of(false);
      }
      return this.http.post<any>(`${this.apiURL}/refresh-token`, { refreshToken }).pipe(
        switchMap(response => {
          this.saveToken(response.token);
          return of(true);
        }),
        catchError(() => of(false))
      );
    }
    return of(false);
  }
  
  ensureAuthenticatedRequest(): Observable<boolean> {
    if (this.isTokenAboutToExpire()) {
      return this.refreshToken();
    }
    return of(true);
  }

  registerUser(user: User): Observable<any> {
    return this.http.post<User>(this.apiURL + '/register', user, { observe: 'response' });
  }

  setRegistredUser(user: User) {
    this.regitredUser = user;
  }

  getRegistredUser(): User {
    return this.regitredUser;
  }

  validateEmail(code: string): Observable<User> {
    return this.http.get<User>(`${this.apiURL}/verifyEmail/${code}`);
  }
}