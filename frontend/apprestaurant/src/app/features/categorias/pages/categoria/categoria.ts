import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CategoriaService } from '../../../../core/services/categoria.service';
import { CategoriaProductoRequest, CategoriaProductoResponse } from '../../../../core/models/categoria.models';
import { ModalComponent } from '../../../../shared/modal/modal';

@Component({
  selector: 'app-categoria',
  imports: [FormsModule, ModalComponent],
  templateUrl: './categoria.html',
  styleUrl: './categoria.scss',
})
export class CategoriaComponent implements OnInit {
  private service = inject(CategoriaService);
  items: CategoriaProductoResponse[] = [];
  showModal = false;
  form: CategoriaProductoRequest = { nombreCategoria: '', descripcionCategoria: '' };
  editId: number | null = null;

  ngOnInit() { this.load(); }

  load() { this.service.findAll().subscribe(data => this.items = data); }

  openNuevo() {
    this.editId = null;
    this.form = { nombreCategoria: '', descripcionCategoria: '' };
    this.showModal = true;
  }

  editar(item: CategoriaProductoResponse) {
    this.editId = item.idCategoria;
    this.form = { nombreCategoria: item.nombreCategoria, descripcionCategoria: item.descripcionCategoria };
    this.showModal = true;
  }

  save() {
    const obs = this.editId
      ? this.service.update(this.editId, this.form)
      : this.service.create(this.form);
    obs.subscribe(() => { this.load(); this.showModal = false; });
  }

  delete(id: number) {
    if (confirm('¿Eliminar categoría?')) this.service.delete(id).subscribe(() => this.load());
  }
}
