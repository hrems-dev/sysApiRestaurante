import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ContabilidadService } from '../../../../core/services/contabilidad.service';
import { VentaDiariaResponse, ReporteContableResponse } from '../../../../core/models/reporte.models';

@Component({
  selector: 'app-contabilidad',
  imports: [FormsModule],
  templateUrl: './contabilidad.html',
  styleUrl: './contabilidad.scss',
})
export class ContabilidadComponent {
  private service = inject(ContabilidadService);
  fechaDiaria = new Date().toISOString().split('T')[0];
  fechaInicio = '';
  fechaFin = '';
  ventaDiaria: VentaDiariaResponse | null = null;
  resumen: ReporteContableResponse | null = null;

  consultarVentasDiarias() {
    this.service.ventasDiarias(this.fechaDiaria).subscribe(data => this.ventaDiaria = data);
  }

  consultarResumen() {
    if (!this.fechaInicio || !this.fechaFin) return;
    this.service.resumen(this.fechaInicio, this.fechaFin).subscribe(data => this.resumen = data);
  }
}
