import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { FacturacionService } from '../../../../core/services/facturacion.service';
import { FacturaRequest, ComprobanteResponse } from '../../../../core/models/facturacion.models';

@Component({
  selector: 'app-facturacion',
  imports: [FormsModule],
  templateUrl: './facturacion.html',
  styleUrl: './facturacion.scss',
})
export class FacturacionComponent implements OnInit {
  private service = inject(FacturacionService);
  items: ComprobanteResponse[] = [];
  editing = false;
  form: FacturaRequest = { idVenta: 0, tipoDocumento: '' };

  ngOnInit() { this.load(); }

  load() { this.service.listarComprobantes().subscribe(data => this.items = data); }

  emitir() {
    this.service.emitirComprobante(this.form).subscribe(() => { this.load(); this.cancel(); });
  }

  cancel() { this.editing = false; this.form = { idVenta: 0, tipoDocumento: '' }; }
}
