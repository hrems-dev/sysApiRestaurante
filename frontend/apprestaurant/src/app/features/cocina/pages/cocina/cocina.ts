import { Component, inject, OnInit } from '@angular/core';
import { CocinaService } from '../../../../core/services/cocina.service';
import { CocinaPedidoResponse } from '../../../../core/models/cocina.models';

@Component({
  selector: 'app-cocina',
  imports: [],
  templateUrl: './cocina.html',
  styleUrl: './cocina.scss',
})
export class CocinaComponent implements OnInit {
  private service = inject(CocinaService);
  pendientes: CocinaPedidoResponse[] = [];
  preparacion: CocinaPedidoResponse[] = [];
  listos: CocinaPedidoResponse[] = [];
  activeTab: 'pendientes' | 'preparacion' | 'listos' = 'pendientes';

  ngOnInit() { this.cargarPendientes(); }

  cargarPendientes() {
    this.service.listarPendientes().subscribe(data => this.pendientes = data);
  }

  cargarPreparacion() {
    this.service.listarPreparacion().subscribe(data => this.preparacion = data);
  }

  cargarListos() {
    this.service.listarListos().subscribe(data => this.listos = data);
  }

  setTab(tab: 'pendientes' | 'preparacion' | 'listos') {
    this.activeTab = tab;
    if (tab === 'pendientes') this.cargarPendientes();
    else if (tab === 'preparacion') this.cargarPreparacion();
    else this.cargarListos();
  }

  cambiarEstado(idPedido: number, estado: string) {
    this.service.cambiarEstado(idPedido, estado).subscribe(() => this.setTab(this.activeTab));
  }
}
