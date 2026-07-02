import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { QRService, LugarService } from '../../../../core/services/lugar.service';
import { QRLugarRequest, QRLugarResponse } from '../../../../core/models/qr.models';

@Component({
  selector: 'app-mesa-qr',
  imports: [FormsModule],
  templateUrl: './mesa-qr.html',
  styleUrl: './mesa-qr.scss',
})
export class MesaQrComponent implements OnInit {
  private qrService = inject(QRService);
  private lugarService = inject(LugarService);
  items: QRLugarResponse[] = [];
  editing = false;
  form: QRLugarRequest = { idLugar: 0, codigoQR: '', urlQR: '' };
  editId: number | null = null;
  lugares: { idLugar: number; nombreLugar: string }[] = [];
  mensaje = '';

  ngOnInit() {
    this.lugarService.findAllActive().subscribe(data => this.lugares = data);
  }

  generarQR() {
    this.qrService.generarQR(this.form).subscribe({
      next: () => { this.mensaje = 'QR generado exitosamente'; this.cancel(); },
      error: () => this.mensaje = 'Error al generar QR'
    });
  }

  desactivar(id: number) {
    if (confirm('¿Desactivar QR?')) this.qrService.desactivarQR(id).subscribe(() => this.mensaje = 'QR desactivado');
  }

  cancel() { this.editing = false; this.editId = null; this.form = { idLugar: 0, codigoQR: '', urlQR: '' }; }
}
