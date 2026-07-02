import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NotificacionService } from '../../../../core/services/notificacion.service';
import { NotificacionRequest, NotificacionResponse } from '../../../../core/models/notificacion.models';

@Component({
  selector: 'app-notificacion',
  imports: [FormsModule],
  templateUrl: './notificacion.html',
  styleUrl: './notificacion.scss',
})
export class NotificacionComponent implements OnInit {
  private service = inject(NotificacionService);
  items: NotificacionResponse[] = [];
  editing = false;
  form: NotificacionRequest = { idUsuario: 0, tipoNotificacion: '', titulo: '', descripcion: '' };
  editId: number | null = null;
  userId = 0;

  ngOnInit() {
    if (this.userId) this.load();
  }

  load() {
    this.service.listarPorUsuario(this.userId).subscribe(data => this.items = data);
  }

  consultar() {
    if (this.userId) this.load();
  }

  save() {
    this.service.create(this.form).subscribe(() => { this.load(); this.cancel(); });
  }

  marcarLeida(id: number) {
    this.service.marcarLeida(id).subscribe(() => this.load());
  }

  delete(id: number) {
    if (confirm('¿Eliminar notificación?')) this.service.delete(id).subscribe(() => this.load());
  }

  cancel() {
    this.editing = false; this.editId = null;
    this.form = { idUsuario: 0, tipoNotificacion: '', titulo: '', descripcion: '' };
  }
}
