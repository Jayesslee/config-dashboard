import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButton, MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { HttpClient } from '@angular/common/http';
import { ConfigDialogComponent } from '../config-dialog/config-dialog.component';
import { Config } from '../../model/Config';
import { ApiService } from '../../service/http-service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    MatButton,
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatDialogModule,
    ConfigDialogComponent,
  ],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  displayedColumns: string[] = ['key', 'value', 'actions'];
  dataSource: Config[] = [];
  snackBar = inject(MatSnackBar);
  apiService = inject(ApiService);
  dialog = inject(MatDialog);

  ngOnInit(): void {
    this.loadParameters();
  }

  loadParameters(): void {
    this.apiService
      .get('config/get/all', {
        pageNum: 0,
        pageSize: 100,
      })
      .subscribe((response: any) => {
        this.dataSource = response.content.map((config: Config) => ({
          key: config.key,
          value: config.value,
        }));
      });
  }

  openEditDialog(parameter?: Config): void {
    const dialogRef = this.dialog.open(ConfigDialogComponent, {
      width: '250px',
      data: parameter ? { ...parameter } : { id: null, name: '', value: '' },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.loadParameters(); // Refresh the table after saving
      }
    });
  }

  deleteParameter(parameter: Config): void {
    this.apiService.delete('config/delete', { key: parameter.key }).subscribe({
      next: () => {
        this.showSuccessMessage('Parameter has been deleted.');
        this.loadParameters();
      },
      error: (err) => {
        this.showErrorMessage(err.error.message || 'Delete failed.');
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
}
