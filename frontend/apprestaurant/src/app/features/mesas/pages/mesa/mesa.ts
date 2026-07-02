import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MesaService } from '../../../../core/services/mesa.service';
import { LugarService } from '../../../../core/services/lugar.service';
import { MesaResponse, MesaRequest } from '../../../../core/models/mesa.models';
import { LugarAtencionResponse } from '../../../../core/models/lugar.models';
import { ModalComponent } from '../../../../shared/modal/modal';

@Component({
  selector: 'app-mesa',
  standalone: true,
  imports: [CommonModule, FormsModule, ModalComponent],
  templateUrl: './mesa.html',
  styleUrl: './mesa.scss',
})
export class MesaComponent implements OnInit {
  private service = inject(MesaService);
  private lugarService = inject(LugarService);

  items: MesaResponse[] = [];
  lugares: LugarAtencionResponse[] = [];
  showModal = false;
  editId: number | null = null;
  form: MesaRequest = { nombreMesa: '', lugar: null, capacidad: 2 };

  ngOnInit() {
    this.load();
    this.lugarService.findAllActive().subscribe(data => this.lugares = data);
  }

  load() { this.service.findAll().subscribe(data => this.items = data); }

  openNuevo() {
    this.editId = null;
    this.form = { nombreMesa: '', lugar: null, capacidad: 2 };
    this.showModal = true;
  }

  editar(item: MesaResponse) {
    this.editId = item.idMesa;
    this.form = {
      nombreMesa: item.nombreMesa,
      lugar: item.lugar ? { idLugar: item.lugar.idLugar } : null,
      capacidad: item.capacidad,
    };
    this.showModal = true;
  }

  save() {
    if (!this.form.nombreMesa || !this.form.lugar) return;
    if (this.editId) {
      this.service.update(this.editId, this.form).subscribe(() => { this.load(); this.showModal = false; });
    } else {
      this.service.save(this.form).subscribe(() => { this.load(); this.showModal = false; });
    }
  }

  eliminar(id: number) {
    if (confirm('¿Desactivar mesa?')) this.service.delete(id).subscribe(() => this.load());
  }

  compareLugar(a: { idLugar: number } | null, b: { idLugar: number } | null): boolean {
    return a?.idLugar === b?.idLugar;
  }
}
