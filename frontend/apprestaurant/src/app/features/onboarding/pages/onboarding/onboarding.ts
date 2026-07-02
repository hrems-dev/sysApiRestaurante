import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { OnboardingService } from '../../../../core/services/onboarding.service';
import { OnboardingRestauranteRequest } from '../../../../core/models/onboarding.models';

@Component({
  selector: 'app-onboarding',
  imports: [FormsModule],
  templateUrl: './onboarding.html',
  styleUrl: './onboarding.scss',
})
export class OnboardingComponent implements OnInit {
  private service = inject(OnboardingService);
  private router = inject(Router);

  restaurante: OnboardingRestauranteRequest = {
    nombreRestaurante: '',
    ruc: '',
    direccion: '',
    telefono: '',
    email: '',
    logoUrl: '',
  };
  mensaje = '';
  loading = false;
  error = '';
  yaConfigurado = false;

  ngOnInit() {
    this.service.obtenerEstado().subscribe({
      next: (res) => {
        if (res.estadoRestaurante) {
          this.yaConfigurado = true;
          this.cargarRestaurante();
        }
      }
    });
  }

  cargarRestaurante() {
    this.service.obtenerRestaurante().subscribe(data => {
      this.restaurante = {
        nombreRestaurante: data.nombreRestaurante,
        ruc: data.ruc,
        direccion: data.direccion,
        telefono: data.telefono,
        email: data.email,
        logoUrl: data.logoUrl,
      };
    });
  }

  guardar() {
    if (!this.restaurante.nombreRestaurante) {
      this.error = 'El nombre del restaurante es obligatorio';
      return;
    }
    this.loading = true;
    this.error = '';
    this.mensaje = '';
    this.service.crearRestaurante(this.restaurante).subscribe({
      next: () => {
        this.mensaje = '¡Restaurante registrado exitosamente!';
        this.loading = false;
        setTimeout(() => this.router.navigate(['/sistema/dashboard']), 1500);
      },
      error: (err) => {
        this.error = err?.error?.mensaje || 'Error al guardar restaurante';
        this.loading = false;
      }
    });
  }

  irDashboard() {
    this.router.navigate(['/sistema/dashboard']);
  }
}
