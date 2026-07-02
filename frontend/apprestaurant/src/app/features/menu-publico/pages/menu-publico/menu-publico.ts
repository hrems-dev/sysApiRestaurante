import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductoService } from '../../../../core/services/producto.service';
import { CategoriaService } from '../../../../core/services/categoria.service';
import { PedidoService } from '../../../../core/services/pedido.service';
import { ProductoResponse } from '../../../../core/models/producto.models';
import { CategoriaProductoResponse } from '../../../../core/models/categoria.models';
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

  productos: ProductoResponse[] = [];
  categorias: CategoriaProductoResponse[] = [];
  productosPorCategoria: { categoria: string; items: ProductoResponse[] }[] = [];

  mesaManual = '';
  mesaIdentificada = '';
  scannerActivo = false;
  scanner: Html5Qrcode | null = null;
  scannerId = 'qr-reader';

  carrito: { producto: ProductoResponse; cantidad: number }[] = [];
  pedidoEnviado = false;
  loading = false;
  idsEnCarrito = new Set<number>();

  noDisponible = false;

  ngOnInit(): void {
    this.categoriaService.findAll().subscribe(cats => {
      this.categorias = cats.filter(c => c.estadoCategoria);
    });
    this.productoService.findAll().subscribe(prods => {
      this.productos = prods.filter(p => p.estadoProducto);
      this.agruparPorCategoria();
      this.noDisponible = this.productos.length === 0;
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
    this.scannerActivo = true;
    this.scanner = new Html5Qrcode(this.scannerId);
    this.scanner.start(
      { facingMode: 'environment' },
      { fps: 10, qrbox: { width: 250, height: 250 } },
      (text) => {
        this.procesarQR(text);
      },
      () => {}
    ).catch(() => {
      this.scannerActivo = false;
    });
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
    } else {
      this.mesaIdentificada = text.trim();
    }
    this.detenerScanner();
  }

  identificarMesa() {
    if (this.mesaManual.trim()) {
      this.mesaIdentificada = this.mesaManual.trim();
      this.mesaManual = '';
    }
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
      idLugar: undefined,
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
