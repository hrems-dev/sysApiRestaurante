import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductoService } from '../../../../core/services/producto.service';
import { CategoriaService } from '../../../../core/services/categoria.service';
import { PedidoService } from '../../../../core/services/pedido.service';
import { LugarService } from '../../../../core/services/lugar.service';
import { ProductoResponse } from '../../../../core/models/producto.models';
import { CategoriaProductoResponse } from '../../../../core/models/categoria.models';
import { LugarAtencionResponse } from '../../../../core/models/lugar.models';
import { Html5Qrcode } from 'html5-qrcode';

@Component({
  selector: 'app-menu-publico',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './menu-publico.html',
  styleUrl: './menu-publico.scss',
})
export class MenuPublicoComponent implements OnInit, OnDestroy {
  private productoService = inject(ProductoService);
  private categoriaService = inject(CategoriaService);
  private pedidoService = inject(PedidoService);
  private lugarService = inject(LugarService);

  productos: ProductoResponse[] = [];
  categorias: CategoriaProductoResponse[] = [];
  productosPorCategoria: { categoria: string; items: ProductoResponse[] }[] = [];
  lugares: LugarAtencionResponse[] = [];

  mesaManual = '';
  mesaIdentificada = '';
  idLugarSeleccionado: number | undefined = undefined;
  scannerActivo = false;
  scanner: Html5Qrcode | null = null;
  scannerId = 'qr-reader';

  carrito: { producto: ProductoResponse; cantidad: number }[] = [];
  pedidoEnviado = false;
  loading = false;
  idsEnCarrito = new Set<number>();

  noDisponible = false;

  modalLugaresAbierto = false;

  ngOnInit(): void {
    this.categoriaService.findAll().subscribe(cats => {
      this.categorias = cats.filter(c => c.estadoCategoria);
    });
    this.productoService.findAll().subscribe(prods => {
      this.productos = prods.filter(p => p.estadoProducto);
      this.agruparPorCategoria();
      this.noDisponible = this.productos.length === 0;
    });
    this.lugarService.findAllActive().subscribe(lugares => {
      this.lugares = lugares;
    });
  }

  ngOnDestroy(): void {
    this.detenerScanner();
  }

  private agruparPorCategoria() {
    const map = new Map<string, ProductoResponse[]>();
    for (const p of this.productos) {
      const key = p.nombreCategoria || 'Sin categoría';
      if (!map.has(key)) map.set(key, []);
      map.get(key)!.push(p);
    }
    this.productosPorCategoria = Array.from(map.entries()).map(([categoria, items]) => ({ categoria, items }));
  }

  productoCesta(prod: ProductoResponse): boolean {
    return this.idsEnCarrito.has(prod.idProducto);
  }

  iniciarScanner() {
    // Selecciona automáticamente la primera MESA disponible (no delivery, no otros tipos)
    const primeraMesa = this.lugares.find(l => l.estadoLugar && l.tipoLugar === 'MESA');
    if (primeraMesa) {
      this.mesaIdentificada = primeraMesa.nombreLugar;
      this.idLugarSeleccionado = primeraMesa.idLugar;
    } else if (this.lugares.length === 0) {
      // Lugares aún no cargados, forzar recarga
      this.lugarService.findAllActive().subscribe(lugares => {
        this.lugares = lugares;
        const mesa = this.lugares.find(l => l.estadoLugar && l.tipoLugar === 'MESA');
        if (mesa) {
          this.mesaIdentificada = mesa.nombreLugar;
          this.idLugarSeleccionado = mesa.idLugar;
        } else {
          alert('No hay mesas disponibles en el sistema');
        }
      });
    } else {
      alert('No hay mesas disponibles (solo delivery u otros tipos)');
    }
  }

  detenerScanner() {
    if (this.scanner) {
      this.scanner.stop().catch(() => {});
      this.scanner = null;
    }
    this.scannerActivo = false;
  }

  private procesarQR(text: string) {
    const match = text.match(/MESA[:=]?\s*(\w+)/i) || text.match(/TABLE[:=]?\s*(\w+)/i);
    if (match) {
      this.mesaIdentificada = match[1];
      const lugar = this.lugares.find(l => l.nombreLugar === this.mesaIdentificada);
      if (lugar) {
        this.idLugarSeleccionado = lugar.idLugar;
      }
    } else {
      this.mesaIdentificada = text.trim();
    }
    this.detenerScanner();
  }

  abrirModalLugares() {
    this.modalLugaresAbierto = true;
  }

  cerrarModalLugares() {
    this.modalLugaresAbierto = false;
  }

  seleccionarLugar(lugar: LugarAtencionResponse) {
    this.mesaIdentificada = lugar.nombreLugar;
    this.idLugarSeleccionado = lugar.idLugar;
    this.cerrarModalLugares();
  }

  cambiarMesa() {
    this.mesaIdentificada = '';
    this.idLugarSeleccionado = undefined;
    this.carrito = [];
    this.idsEnCarrito.clear();
    this.pedidoEnviado = false;
  }

  agregarAlCarrito(producto: ProductoResponse) {
    const existente = this.carrito.find(c => c.producto.idProducto === producto.idProducto);
    if (existente) {
      existente.cantidad++;
    } else {
      this.carrito.push({ producto, cantidad: 1 });
      this.idsEnCarrito.add(producto.idProducto);
    }
  }

  quitarDelCarrito(producto: ProductoResponse) {
    const idx = this.carrito.findIndex(c => c.producto.idProducto === producto.idProducto);
    if (idx >= 0) {
      if (this.carrito[idx].cantidad > 1) {
        this.carrito[idx].cantidad--;
      } else {
        this.carrito.splice(idx, 1);
        this.idsEnCarrito.delete(producto.idProducto);
      }
    }
  }

  totalCarrito(): number {
    return this.carrito.reduce((sum, c) => sum + c.producto.precioProducto * c.cantidad, 0);
  }

  enviarPedido() {
    if (!this.mesaIdentificada || this.carrito.length === 0) return;
    this.loading = true;
    this.pedidoEnviado = false;

    const pedido = {
      idUsuario: 0,
      idLugar: this.idLugarSeleccionado,
      tipoPedido: 'MESA',
      direccionEntrega: this.mesaIdentificada,
      observacionPedido: `Pedido desde Menú Público - Mesa ${this.mesaIdentificada}`,
      detalles: this.carrito.map(c => ({
        idProducto: c.producto.idProducto,
        cantidad: c.cantidad,
        precioUnitario: c.producto.precioProducto,
      })),
    };

    this.pedidoService.create(pedido).subscribe({
      next: () => {
        this.pedidoEnviado = true;
        this.loading = false;
        this.carrito = [];
        this.idsEnCarrito.clear();
      },
      error: () => {
        this.loading = false;
      },
    });
  }
}
