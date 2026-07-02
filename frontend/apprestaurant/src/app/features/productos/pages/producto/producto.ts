import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ProductoService } from '../../../../core/services/producto.service';
import { CategoriaService } from '../../../../core/services/categoria.service';
import { ProductoRequest, ProductoResponse } from '../../../../core/models/producto.models';
import { ModalComponent } from '../../../../shared/modal/modal';

@Component({
  selector: 'app-producto',
  imports: [FormsModule, ModalComponent],
  templateUrl: './producto.html',
  styleUrl: './producto.scss',
})
export class ProductoComponent implements OnInit {
  private service = inject(ProductoService);
  private categoriaService = inject(CategoriaService);
  items: ProductoResponse[] = [];
  showModal = false;
  updatingStock = false;
  stockValue = 0;
  stockProductoId: number | null = null;
  form: ProductoRequest = { idCategoria: 0, nombreProducto: '', descripcionProducto: '', precioProducto: 0, stockProducto: 0 };
  editId: number | null = null;
  categorias: { idCategoria: number; nombreCategoria: string }[] = [];

  ngOnInit() {
    this.categoriaService.findAll().subscribe(data => this.categorias = data);
    this.load();
  }

  load() { this.service.findAll().subscribe(data => this.items = data); }

  openNuevo() {
    this.editId = null;
    this.form = { idCategoria: 0, nombreProducto: '', descripcionProducto: '', precioProducto: 0, stockProducto: 0 };
    this.showModal = true;
  }

  editar(item: ProductoResponse) {
    this.editId = item.idProducto;
    this.form = { idCategoria: item.idCategoria, nombreProducto: item.nombreProducto, descripcionProducto: item.descripcionProducto, precioProducto: item.precioProducto, stockProducto: item.stockProducto };
    this.showModal = true;
  }

  save() {
    const obs = this.editId
      ? this.service.update(this.editId, this.form)
      : this.service.create(this.form);
    obs.subscribe(() => { this.load(); this.showModal = false; });
  }

  delete(id: number) {
    if (confirm('¿Eliminar producto?')) this.service.delete(id).subscribe(() => this.load());
  }

  actualizarStock(id: number) {
    this.service.updateStock(id, { stock: this.stockValue }).subscribe(() => { this.load(); this.updatingStock = false; this.stockProductoId = null; this.stockValue = 0; });
  }
}
