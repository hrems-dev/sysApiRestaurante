import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ReporteService } from '../../../../core/services/reporte.service';
import { ReporteVentasResponse, ReportePedidosResponse, ReporteReservasResponse, ReporteEntregasResponse } from '../../../../core/models/reporte.models';

@Component({
  selector: 'app-reportes',
  imports: [FormsModule, CommonModule],
  templateUrl: './reportes.html',
  styleUrl: './reportes.scss',
})
export class ReportesComponent {
  private service = inject(ReporteService);
  fechaInicio = '';
  fechaFin = '';
  reporteVentas: ReporteVentasResponse | null = null;
  reportePedidos: ReportePedidosResponse | null = null;
  reporteReservas: ReporteReservasResponse | null = null;
  reporteEntregas: ReporteEntregasResponse[] = [];

  consultarVentas() {
    if (!this.fechaInicio || !this.fechaFin) return;
    this.service.reporteVentas(this.fechaInicio, this.fechaFin).subscribe(data => this.reporteVentas = data);
  }

  consultarPedidos() {
    if (!this.fechaInicio || !this.fechaFin) return;
    this.service.reportePedidos(this.fechaInicio, this.fechaFin).subscribe(data => this.reportePedidos = data);
  }

  consultarReservas() {
    if (!this.fechaInicio || !this.fechaFin) return;
    this.service.reporteReservas(this.fechaInicio, this.fechaFin).subscribe(data => this.reporteReservas = data);
  }

  consultarEntregas() {
    if (!this.fechaInicio || !this.fechaFin) return;
    this.service.reporteEntregas(this.fechaInicio, this.fechaFin).subscribe(data => this.reporteEntregas = data);
  }
}
