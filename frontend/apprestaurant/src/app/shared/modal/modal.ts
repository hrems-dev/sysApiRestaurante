import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-modal',
  standalone: true,
  template: `
    @if (open) {
      <div class="modal-overlay" (click)="close.emit()">
        <div class="modal-content" (click)="$event.stopPropagation()">
          <div class="modal-header">
            <h3>{{ title }}</h3>
            <button class="modal-close-btn" (click)="close.emit()">&times;</button>
          </div>
          <div class="modal-body">
            <ng-content />
          </div>
        </div>
      </div>
    }
  `,
  styles: [`
    .modal-overlay {
      position: fixed; inset: 0; background: rgba(0,0,0,0.5);
      display: flex; align-items: center; justify-content: center;
      z-index: 1000; padding: 20px;
    }
    .modal-content {
      background: #fff; border-radius: 16px;
      width: 100%; max-width: 520px;
      max-height: 90vh; overflow-y: auto;
      box-shadow: 0 20px 60px rgba(0,0,0,0.3);
      animation: modalIn 0.2s ease-out;
    }
    @keyframes modalIn {
      from { opacity: 0; transform: scale(0.95) translateY(10px); }
      to { opacity: 1; transform: scale(1) translateY(0); }
    }
    .modal-header {
      display: flex; align-items: center; justify-content: space-between;
      padding: 20px 24px 0;
    }
    .modal-header h3 { font-size: 18px; font-weight: 700; color: #1e293b; margin: 0; }
    .modal-close-btn {
      background: none; border: none; font-size: 28px; cursor: pointer;
      color: #94a3b8; line-height: 1; padding: 0 4px;
    }
    .modal-close-btn:hover { color: #1e293b; }
    .modal-body { padding: 20px 24px 24px; }
  `],
})
export class ModalComponent {
  @Input({ required: true }) title = '';
  @Input() open = false;
  @Output() close = new EventEmitter<void>();
}
