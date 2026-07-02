import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ReservaService } from '../../../../core/services/reserva.service';
import { ReservaRequest, ReservaResponse } from '../../../../core/models/reserva.models';

@Component({
  selector: 'app-reserva',
  imports: [FormsModule],
  templateUrl: './reserva.html',
  styleUrl: './reserva.scss',
})
export class ReservaComponent implements OnInit {
  private service = inject(ReservaService);
  items: ReservaResponse[] = [];
  editing = false;
  form: ReservaRequest = { idUsuario: 0, idLugar: 0, fechaHoraReserva: '', cantidadPersonas: 1, observacionReserva: '' };
  editId: number | null = null;

  ngOnInit() { this.load(); }

  load() { this.service.findAll().subscribe(data => this.items = data); }

  save() {
    this.service.create(this.form).subscribe(() => { this.load(); this.cancel(); });
  }

  confirmar(id: number) { this.service.confirmarReserva(id).subscribe(() => this.load()); }

  cancelarReserva(id: number) { this.service.cancelarReserva(id).subscribe(() => this.load()); }

  delete(id: number) {
    if (confirm('¿Eliminar reserva?')) this.service.delete(id).subscribe(() => this.load());
  }

  cancel() {
    this.editing = false; this.editId = null;
    this.form = { idUsuario: 0, idLugar: 0, fechaHoraReserva: '', cantidadPersonas: 1, observacionReserva: '' };
  }
}
