import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { ApiService } from '../../service/http-service';
import { CommonModule } from '@angular/common';
import { UserAuth } from '../../model/UserAuth';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSnackBarModule,
    MatCardModule,
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  loginForm: FormGroup;

  apiService = inject(ApiService);
  router = inject(Router);
  snackBar = inject(MatSnackBar);

  constructor(private fb: FormBuilder) {
    this.loginForm = this.fb.group({
      userName: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  onLogin() {
    if (this.loginForm.invalid) {
      return;
    }

    const userAuth: UserAuth = this.loginForm.value;
    this.apiService.login(userAuth).subscribe({
      next: (response: HttpResponse<any>) => {
        if (response.status === 200) {
          const token = response.headers.get('Authorization');
          if (token) {
            localStorage.setItem('token', token);
            this.showSuccessMessage('Login Success');
            this.router.navigateByUrl('dashboard');
          }
        }
      },
      error: (error) => {
        this.showErrorMessage('Login failed. Please check your credentials.');
      },
    });
  }

  showSuccessMessage(message: string) {
    this.snackBar.open(message, 'Close', {
      duration: 3000,
      panelClass: ['success-snackbar'],
    });
  }

  showErrorMessage(message: string) {
    this.snackBar.open(message, 'Close', {
      duration: 3000,
      panelClass: ['error-snackbar'],
    });
  }

  onRegister() {
    this.router.navigateByUrl('/register');
  }
}
