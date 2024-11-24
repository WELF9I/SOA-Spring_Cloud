import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../model/user.model';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styles: [
  ]
})
export class LoginComponent implements OnInit {

  user = new User();
  err: number = 0;
  message: string = "login ou mot de passe erronés..";

  constructor(private authService: AuthService,
              private router: Router) { }

  ngOnInit(): void {
  }

  onLoggedin() {
    this.authService.login(this.user).subscribe({
      next: (data) => {
        let jwToken = data.headers.get('Authorization');
        if (jwToken) {
          // Remove 'Bearer ' prefix if present
          jwToken = jwToken.replace('Bearer ', '');
          this.authService.saveToken(jwToken);
          this.router.navigate(['/']);
        } else {
          console.error("No token found in response headers");
          this.err = 1;
        }
      },
      error: (err) => {
        this.err = 1;
        if (err.error?.errorCause === 'disabled') {
          this.message = "Utilisateur désactivé, Veuillez contacter votre Administrateur";
        }
      }
    });
  }
  
}