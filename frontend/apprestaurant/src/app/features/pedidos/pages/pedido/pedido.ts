import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PedidoService } from '../../../../core/services/pedido.service';
import { PedidoResponse } from '../../../../core/models/pedido.models';

@Component({
  selector: 'app-pedido',
  imports: [FormsModule],
  templateUrl: './pedido.html',
  styleUrl: './pedido.scss',
})
export class PedidoComponent implements OnInit {
  private service = inject(PedidoService);
  items: PedidoResponse[] = [];
  expandedId: number | null = null;

  ngOnInit() { this.load(); }

  load() { this.service.findAll().subscribe(data => this.items = data); }

  toggleExpand(id: number) { this.expandedId = this.expandedId === id ? null : id; }

  cambiarEstado(id: number, estado: string) {
    this.service.cambiarEstado(id, { estado }).subscribe(() => this.load());
  }

  marcarPagado(id: number) {
    this.service.marcarPagado(id).subscribe(() => this.load());
  }

  delete(id: number) {
    if (confirm('¿Anular pedido?')) this.service.delete(id).subscribe(() => this.load());
  }
}
