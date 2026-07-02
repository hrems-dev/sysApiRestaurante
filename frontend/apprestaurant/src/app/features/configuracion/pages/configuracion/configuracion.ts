import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ConfiguracionService } from '../../../../core/services/configuracion.service';
import { ConfiguracionRequest, ConfiguracionResponse } from '../../../../core/models/configuracion.models';

@Component({
  selector: 'app-configuracion',
  imports: [FormsModule],
  templateUrl: './configuracion.html',
  styleUrl: './configuracion.scss',
})
export class ConfiguracionComponent implements OnInit {
  private service = inject(ConfiguracionService);
  items: ConfiguracionResponse[] = [];
  editing = false;
  form: ConfiguracionRequest = { clave: '', valor: '', descripcion: '' };
  editId: number | null = null;

  ngOnInit() { this.load(); }

  load() { this.service.obtenerTodas().subscribe(data => this.items = data); }

  save() {
    if (this.editId) {
      this.service.actualizar(this.editId, this.form).subscribe(() => { this.load(); this.cancel(); });
    } else {
      this.service.crear(this.form).subscribe(() => { this.load(); this.cancel(); });
    }
  }

  edit(item: ConfiguracionResponse) {
    this.editId = item.idConfig;
    this.form = { clave: item.clave, valor: item.valor, descripcion: item.descripcion };
    this.editing = true;
  }

  delete(id: number) {
    if (confirm('¿Eliminar?')) this.service.eliminar(id).subscribe(() => this.load());
  }

  cancel() { this.editing = false; this.editId = null; this.form = { clave: '', valor: '', descripcion: '' }; }
}
