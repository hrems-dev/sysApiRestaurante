import { Component, inject, OnInit, signal } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { AuthService } from '../../core/services/auth';
import { OnboardingService } from '../../core/services/onboarding.service';
import { EstadoModulosResponse } from '../../core/models/onboarding.models';

interface MenuItem {
  path: string;
  label: string;
  icon: string;
  necesitaBasicos?: boolean;
}

@Component({
  selector: 'app-main-layout',
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './main-layout.html',
  styleUrl: './main-layout.scss',
})
export class MainLayoutComponent implements OnInit {
  private auth = inject(AuthService);
  private onboardingService = inject(OnboardingService);

  sidebarOpen = signal(true);
  username = signal(this.auth.getUsername());
  role = signal(this.auth.getRole());
  estadoModulos = signal<EstadoModulosResponse | null>(null);

  menuItems: MenuItem[] = [
    { path: '/sistema/dashboard', label: 'Dashboard', icon: 'fa-solid fa-gauge-high' },
    { path: '/sistema/onboarding', label: 'Onboarding', icon: 'fa-solid fa-rocket' },
    { path: '/sistema/configuracion', label: 'Configuración', icon: 'fa-solid fa-gear' },
    { path: '/sistema/usuarios', label: 'Usuarios', icon: 'fa-solid fa-users' },
    { path: '/sistema/roles', label: 'Roles', icon: 'fa-solid fa-user-tag' },
    { path: '/sistema/lugares', label: 'Lugares', icon: 'fa-solid fa-location-dot', necesitaBasicos: true },
    { path: '/sistema/mesas', label: 'Mesas', icon: 'fa-solid fa-table', necesitaBasicos: true },
    { path: '/sistema/mesas-qr', label: 'Mesas QR', icon: 'fa-solid fa-qrcode', necesitaBasicos: true },
    { path: '/sistema/categorias', label: 'Categorías', icon: 'fa-solid fa-folder-tree', necesitaBasicos: true },
    { path: '/sistema/productos', label: 'Productos', icon: 'fa-solid fa-utensils', necesitaBasicos: true },
    { path: '/sistema/pedidos', label: 'Pedidos', icon: 'fa-solid fa-clipboard-list', necesitaBasicos: true },
    { path: '/sistema/pagos', label: 'Pagos', icon: 'fa-solid fa-credit-card', necesitaBasicos: true },
    { path: '/sistema/reservas', label: 'Reservas', icon: 'fa-solid fa-calendar-check', necesitaBasicos: true },
    { path: '/sistema/ventas', label: 'Ventas', icon: 'fa-solid fa-file-invoice', necesitaBasicos: true },
    { path: '/sistema/cocina', label: 'Cocina', icon: 'fa-solid fa-kitchen-set', necesitaBasicos: true },
    { path: '/sistema/contabilidad', label: 'Contabilidad', icon: 'fa-solid fa-chart-line', necesitaBasicos: true },
    { path: '/sistema/delivery', label: 'Delivery', icon: 'fa-solid fa-truck', necesitaBasicos: true },
    { path: '/sistema/facturacion', label: 'Facturación', icon: 'fa-solid fa-file-invoice-dollar', necesitaBasicos: true },
    { path: '/sistema/reportes', label: 'Reportes', icon: 'fa-solid fa-chart-bar', necesitaBasicos: true },
    { path: '/sistema/notificaciones', label: 'Notificaciones', icon: 'fa-solid fa-bell' },
  ];

  readonly sections: { items: MenuItem[]; title: string }[] = [
    { items: this.menuItems.slice(0, 5), title: 'PRINCIPAL' },
    { items: this.menuItems.slice(5, 15), title: 'OPERACIONES' },
    { items: this.menuItems.slice(15), title: 'REPORTES' },
  ];

  ngOnInit() {
    this.onboardingService.obtenerEstadoModulos().subscribe(res => {
      this.estadoModulos.set(res);
    });
  }

  isLocked(item: MenuItem): boolean {
    const estado = this.estadoModulos();
    if (!estado) return true;
    if (!item.necesitaBasicos) return false;
    return !estado.restauranteRegistrado;
  }

  get lockedTooltip(): string {
    return 'Registra el restaurante en Onboarding para acceder';
  }

  toggleSidebar() {
    this.sidebarOpen.update(v => !v);
  }

  logout() {
    this.auth.logout();
  }
}
