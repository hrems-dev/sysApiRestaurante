import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UsuarioService } from '../../../../core/services/usuario.service';
import { UserModels } from '../../../../core/models/user.models';

@Component({
  selector: 'app-usuario',
  imports: [FormsModule],
  templateUrl: './usuario.html',
  styleUrl: './usuario.scss',
})
export class UsuarioComponent implements OnInit {
  private service = inject(UsuarioService);
  items: UserModels[] = [];
  editing = false;
  form: Partial<UserModels> = { nombreUsuario: '', nombres: '', apellidos: '', email: '', telefono: '' };
  editId: string | null = null;

  ngOnInit() { this.load(); }

  load() { this.service.findAll().subscribe(data => this.items = data); }

  save() {
    if (this.editId) {
      this.service.update(Number(this.editId), this.form).subscribe(() => { this.load(); this.cancel(); });
    } else {
      this.service.save(this.form).subscribe(() => { this.load(); this.cancel(); });
    }
  }

  edit(item: UserModels) {
    this.editId = item.idUsuario;
    this.form = { nombreUsuario: item.nombreUsuario, nombres: item.nombres, apellidos: item.apellidos, email: item.email, telefono: item.telefono };
    this.editing = true;
  }

  delete(id: string) {
    if (confirm('¿Eliminar usuario?')) this.service.delete(Number(id)).subscribe(() => this.load());
  }

  cancel() { this.editing = false; this.editId = null; this.form = { nombreUsuario: '', nombres: '', apellidos: '', email: '', telefono: '' }; }
}
