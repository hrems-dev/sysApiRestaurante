import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { LugarAtencionService } from '../../../../core/services/lugar-atencion';
import { LugarAtencion, QRLugar } from '../../../../core/models/lugar-atencion';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss',
})
export class DashboardComponent implements OnInit {
  private fb = inject(FormBuilder);
  private lugarService = inject(LugarAtencionService);

  lugares: LugarAtencion[] = [];
  qrGenerado: QRLugar | null = null;
  loading = false;
  errorMsg = '';
  successMsg = '';

  lugarForm = this.fb.nonNullable.group({
    nombreLugar: ['', [Validators.required, Validators.minLength(3)]],
    tipoLugar: ['mesa', [Validators.required]],
    direccion: [''],
    capacidadMaxima: [0],
    estadoLugar: [true],
    observacion: [''],
  });

  ngOnInit(): void {
    this.loadLugares();
  }

  loadLugares(): void {
    this.lugarService.getAll().subscribe({
      next: (data) => (this.lugares = data),
      error: () => (this.errorMsg = 'No se pudieron cargar los lugares'),
    });
  }

  onSubmit(): void {
    if (this.lugarForm.invalid) {
      this.lugarForm.markAllAsTouched();
      return;
    }

    this.loading = true;
    this.errorMsg = '';
    this.successMsg = '';

    const data: LugarAtencion = {
      nombreLugar: this.lugarForm.getRawValue().nombreLugar,
      tipoLugar: this.lugarForm.getRawValue().tipoLugar,
      direccion: this.lugarForm.getRawValue().direccion || undefined,
      capacidadMaxima: this.lugarForm.getRawValue().capacidadMaxima || undefined,
      estadoLugar: this.lugarForm.getRawValue().estadoLugar,
      observacion: this.lugarForm.getRawValue().observacion || undefined,
    };

    this.lugarService.create(data).subscribe({
      next: () => {
        this.loading = false;
        this.successMsg = 'Lugar creado correctamente';
        this.lugarForm.reset({ nombreLugar: '', tipoLugar: 'mesa', direccion: '', capacidadMaxima: 0, estadoLugar: true, observacion: '' });
        this.loadLugares();
      },
      error: (err) => {
        this.loading = false;
        this.errorMsg = err?.error?.message || 'No se pudo crear el lugar';
      },
    });
  }

  generarQR(id?: number): void {
    if (!id) {
      return;
    }

    this.lugarService.generarQR(id).subscribe({
      next: (qr) => {
        this.qrGenerado = qr;
        this.successMsg = 'QR generado correctamente';
      },
      error: (err) => {
        this.errorMsg = err?.error?.message || 'No se pudo generar el QR';
      },
    });
  }
}
