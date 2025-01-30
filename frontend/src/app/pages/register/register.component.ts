import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatCardModule } from '@angular/material/card';
import { Router } from '@angular/router';
import { ApiService } from '../../service/http-service';
import { UserAuth } from '../../model/UserAuth';

@Component({
  selector: 'app-register',
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
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  private fb = inject(FormBuilder);
  private apiService = inject(ApiService);
  private snackBar = inject(MatSnackBar);
  private router = inject(Router);

  registerForm: FormGroup = this.fb.group({
    userName: ['', [Validators.required, Validators.minLength(3)]],
    password: ['', [Validators.required, Validators.minLength(6)]],
    confirmPassword: ['', [Validators.required]],
  });

  onRegister() {
    if (this.registerForm.invalid) return;

    const formData = this.registerForm.value;
    if (formData.password !== formData.confirmPassword) {
      this.snackBar.open('Passwords do not match', 'Close', { duration: 3000 });
      return;
    }

    const userAuth: UserAuth = {
      userName: formData.userName,
      password: formData.password,
    };

    this.apiService.register(userAuth).subscribe(
      () => {
        this.snackBar.open(
          'Registration Successful! Redirecting to login...',
          'Close',
          { duration: 3000 }
        );
        setTimeout(() => this.router.navigateByUrl('/login'), 2000);
      },
      (error) => {
        this.snackBar.open(`Registration Failed: ${error.message}`, 'Close', {
          duration: 3000,
        });
      }
    );
  }
}
