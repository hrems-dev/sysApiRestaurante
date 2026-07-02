import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PagoService, MetodoPagoService } from '../../../../core/services/pago.service';
import { PagoRequest, PagoResponse } from '../../../../core/models/pago.models';

@Component({
  selector: 'app-pago',
  imports: [FormsModule],
  templateUrl: './pago.html',
  styleUrl: './pago.scss',
})
export class PagoComponent implements OnInit {
  private service = inject(PagoService);
  private metodoPagoService = inject(MetodoPagoService);
  items: PagoResponse[] = [];
  editing = false;
  form: PagoRequest = { idMetodoPago: 0, codigoPago: '', montoPago: 0, referenciaPago: '' };
  editId: number | null = null;
  metodosPago: any[] = [];

  ngOnInit() {
    this.metodoPagoService.findAllActive().subscribe(data => this.metodosPago = data);
    this.load();
  }

  load() { this.service.findAll().subscribe(data => this.items = data); }

  save() {
    this.service.create(this.form).subscribe(() => { this.load(); this.cancel(); });
  }

  validarPago(id: number, codigo: string) {
    this.service.validarPago(id, { codigoPago: codigo, estadoPago: 'COMPLETADO' }).subscribe(() => this.load());
  }

  delete(id: number) {
    if (confirm('¿Anular pago?')) this.service.delete(id).subscribe(() => this.load());
  }

  cancel() { this.editing = false; this.editId = null; this.form = { idMetodoPago: 0, codigoPago: '', montoPago: 0, referenciaPago: '' }; }
}
