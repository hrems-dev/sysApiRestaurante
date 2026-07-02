import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RolService } from '../../../../core/services/usuario.service';
import { RoleModels } from '../../../../core/models/role.models';
import { ModalComponent } from '../../../../shared/modal/modal';

@Component({
  selector: 'app-rol',
  standalone: true,
  imports: [CommonModule, FormsModule, ModalComponent],
  templateUrl: './rol.html',
  styleUrl: './rol.scss',
})
export class RolComponent implements OnInit {
  private service = inject(RolService);

  items: RoleModels[] = [];
  showModal = false;
  editId: number | null = null;
  form: Partial<RoleModels> = { nombreRol: '', descripcionRol: '' };

  ngOnInit() { this.load(); }

  load() { this.service.findAll().subscribe(data => this.items = data); }

  openNuevo() {
    this.editId = null;
    this.form = { nombreRol: '', descripcionRol: '' };
    this.showModal = true;
  }

  editar(item: RoleModels) {
    this.editId = item.idRol;
    this.form = { nombreRol: item.nombreRol, descripcionRol: item.descripcionRol };
    this.showModal = true;
  }

  save() {
    if (!this.form.nombreRol) return;
    if (this.editId) {
      this.service.update(this.editId, { ...this.form, estadoRol: true }).subscribe(() => { this.load(); this.showModal = false; });
    } else {
      this.service.save({ ...this.form, estadoRol: true }).subscribe(() => { this.load(); this.showModal = false; });
    }
  }

  eliminar(id: number) {
    if (confirm('¿Eliminar rol?')) this.service.delete(id).subscribe(() => this.load());
  }
}
