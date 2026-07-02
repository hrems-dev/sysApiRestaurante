import { Component, inject, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { PedidoService } from '../../../../core/services/pedido.service';
import { VentaService } from '../../../../core/services/venta.service';
import { ProductoService } from '../../../../core/services/producto.service';
import { UsuarioService } from '../../../../core/services/usuario.service';
import { OnboardingService } from '../../../../core/services/onboarding.service';
import { ImportacionExcelResponse } from '../../../../core/models/onboarding.models';

@Component({
  selector: 'app-dashboard',
  imports: [RouterLink],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss',
})
export class DashboardComponent implements OnInit {
  private pedidoService = inject(PedidoService);
  private ventaService = inject(VentaService);
  private productoService = inject(ProductoService);
  private usuarioService = inject(UsuarioService);
  private onboardingService = inject(OnboardingService);

  totalPedidos = 0;
  totalVentas = 0;
  totalProductos = 0;
  totalUsuarios = 0;

  excelFile: File | null = null;
  excelLoading = false;
  excelResultado: ImportacionExcelResponse | null = null;

  ngOnInit() {
    this.pedidoService.findAll().subscribe(data => this.totalPedidos = data.length);
    this.ventaService.findAll().subscribe(data => this.totalVentas = data.length);
    this.productoService.findAll().subscribe(data => this.totalProductos = data.length);
    this.usuarioService.findAll().subscribe(data => this.totalUsuarios = data.length);
  }

  descargarPlantilla() {
    this.onboardingService.descargarPlantilla().subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'plantilla_datos_restaurante.xlsx';
      a.click();
      window.URL.revokeObjectURL(url);
    });
  }

  onFileChange(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.excelFile = input.files[0];
      this.excelResultado = null;
    }
  }

  importarExcel() {
    if (!this.excelFile) return;
    this.excelLoading = true;
    this.excelResultado = null;
    this.onboardingService.importarExcel(this.excelFile).subscribe({
      next: (res) => {
        this.excelResultado = res;
        this.excelLoading = false;
        this.excelFile = null;
      },
      error: (err) => {
        this.excelResultado = err?.error || {
          totalImportados: 0,
          totalErrores: 1,
          errores: [err?.message || 'Error de conexión'],
          mensaje: 'Error al importar'
        };
        this.excelLoading = false;
      }
    });
  }
}
