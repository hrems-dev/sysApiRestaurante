import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DeliveryService } from '../../../../core/services/delivery.service';
import { DeliveryResponse, DeliveryRequest } from '../../../../core/models/delivery.models';

@Component({
  selector: 'app-delivery',
  imports: [FormsModule],
  templateUrl: './delivery.html',
  styleUrl: './delivery.scss',
})
export class DeliveryComponent implements OnInit {
  private service = inject(DeliveryService);
  items: DeliveryResponse[] = [];
  editing = false;
  form: DeliveryRequest = { idPedido: 0, idRepartidor: 0, direccionEntrega: '' };

  ngOnInit() { this.load(); }

  load() { this.service.listarPedidosDelivery().subscribe(data => this.items = data); }

  asignar() {
    this.service.asignarRepartidor(this.form).subscribe(() => { this.load(); this.cancel(); });
  }

  actualizarEstado(idPedido: number, estado: string) {
    this.service.actualizarEstado(idPedido, estado).subscribe(() => this.load());
  }

  cancel() { this.editing = false; this.form = { idPedido: 0, idRepartidor: 0, direccionEntrega: '' }; }
}
