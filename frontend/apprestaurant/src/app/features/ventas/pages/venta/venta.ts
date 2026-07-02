import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { VentaService } from '../../../../core/services/venta.service';
import { VentaRequest, VentaResponse } from '../../../../core/models/venta.models';

@Component({
  selector: 'app-venta',
  imports: [FormsModule],
  templateUrl: './venta.html',
  styleUrl: './venta.scss',
})
export class VentaComponent implements OnInit {
  private service = inject(VentaService);
  items: VentaResponse[] = [];
  editing = false;
  form: VentaRequest = { idPedido: 0, idPago: 0, tipoDocumento: '' };
  editId: number | null = null;

  ngOnInit() { this.load(); }

  load() { this.service.findAll().subscribe(data => this.items = data); }

  save() {
    this.service.create(this.form).subscribe(() => { this.load(); this.cancel(); });
  }

  cerrar(id: number) { this.service.cerrarVenta(id).subscribe(() => this.load()); }

  anular(id: number) {
    if (confirm('¿Anular venta?')) this.service.anularVenta(id).subscribe(() => this.load());
  }

  cancel() { this.editing = false; this.editId = null; this.form = { idPedido: 0, idPago: 0, tipoDocumento: '' }; }
}
