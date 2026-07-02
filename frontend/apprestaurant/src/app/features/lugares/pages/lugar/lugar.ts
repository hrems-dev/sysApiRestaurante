import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LugarService } from '../../../../core/services/lugar.service';
import { LugarAtencionRequest, LugarAtencionResponse } from '../../../../core/models/lugar.models';

@Component({
  selector: 'app-lugar',
  imports: [FormsModule],
  templateUrl: './lugar.html',
  styleUrl: './lugar.scss',
})
export class LugarComponent implements OnInit {
  private service = inject(LugarService);
  items: LugarAtencionResponse[] = [];
  editing = false;
  form: LugarAtencionRequest = { nombreLugar: '', tipoLugar: '', direccion: '', capacidadMaxima: 0, observacion: '' };
  editId: number | null = null;

  ngOnInit() { this.load(); }

  load() { this.service.findAll().subscribe(data => this.items = data); }

  save() {
    if (this.editId) {
      this.service.update(this.editId, this.form).subscribe(() => { this.load(); this.cancel(); });
    } else {
      this.service.create(this.form).subscribe(() => { this.load(); this.cancel(); });
    }
  }

  edit(item: LugarAtencionResponse) {
    this.editId = item.idLugar;
    this.form = { nombreLugar: item.nombreLugar, tipoLugar: item.tipoLugar, direccion: item.direccion, capacidadMaxima: item.capacidadMaxima, observacion: item.observacion };
    this.editing = true;
  }

  delete(id: number) {
    if (confirm('¿Eliminar lugar?')) this.service.delete(id).subscribe(() => this.load());
  }

  cancel() { this.editing = false; this.editId = null; this.form = { nombreLugar: '', tipoLugar: '', direccion: '', capacidadMaxima: 0, observacion: '' }; }
}
