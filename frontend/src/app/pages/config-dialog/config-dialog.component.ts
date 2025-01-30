import { Component, inject, Inject } from '@angular/core';
import {
  MatDialogRef,
  MAT_DIALOG_DATA,
  MatDialogModule,
  MatDialog,
} from '@angular/material/dialog';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Config } from '../../model/Config';
import { ApiService } from '../../service/http-service';

@Component({
  selector: 'app-config-dialog',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatDialogModule,
  ],
  templateUrl: './config-dialog.component.html',
  styleUrl: './config-dialog.component.css',
})
export class ConfigDialogComponent {
  parameterForm: FormGroup;
  apiService = inject(ApiService);

  constructor(
    public dialogRef: MatDialogRef<ConfigDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Config,
    private fb: FormBuilder,
    private http: HttpClient
  ) {
    this.parameterForm = this.fb.group({
      key: [data.key || '', Validators.required],
      value: [data.value || '', Validators.required],
    });
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  onSave(): void {
    if (this.parameterForm.invalid) {
      this.parameterForm.markAllAsTouched();
      return;
    }

    const formData = this.parameterForm.value;
    const updateRequest: Config = {
      key: formData.key,
      value: formData.value,
    };
    this.apiService.post('config/save', updateRequest).subscribe(() => {
      this.dialogRef.close(updateRequest);
    });
  }


}
