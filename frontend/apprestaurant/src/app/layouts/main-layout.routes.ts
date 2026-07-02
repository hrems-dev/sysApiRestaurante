import { Routes } from '@angular/router';
import { MainLayoutComponent } from './main-layout/main-layout';
import { configuracionGuard } from '../core/guards/configuracion.guard';

export const MAIN_ROUTES: Routes = [
  {
    path: '',
    component: MainLayoutComponent,
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      {
        path: 'dashboard',
        loadComponent: () =>
          import('../features/dashboard/pages/dashboard/dashboard').then(
            (m) => m.DashboardComponent,
          ),
      },
      {
        path: 'onboarding',
        loadComponent: () =>
          import('../features/onboarding/pages/onboarding/onboarding').then(
            (m) => m.OnboardingComponent,
          ),
      },
      {
        path: 'configuracion',
        loadComponent: () =>
          import('../features/configuracion/pages/configuracion/configuracion').then(
            (m) => m.ConfiguracionComponent,
          ),
      },
      {
        path: 'usuarios',
        loadComponent: () =>
          import('../features/usuarios/pages/usuario/usuario').then(
            (m) => m.UsuarioComponent,
          ),
      },
      {
        path: 'roles',
        loadComponent: () =>
          import('../features/roles/pages/rol/rol').then(
            (m) => m.RolComponent,
          ),
      },
      {
        path: 'mesas',
        loadComponent: () =>
          import('../features/mesas/pages/mesa/mesa').then(
            (m) => m.MesaComponent,
          ),
        canActivate: [configuracionGuard],
      },
      {
        path: 'lugares',
        loadComponent: () =>
          import('../features/lugares/pages/lugar/lugar').then(
            (m) => m.LugarComponent,
          ),
        canActivate: [configuracionGuard],
      },
      {
        path: 'mesas-qr',
        loadComponent: () =>
          import('../features/mesas-qr/pages/mesa-qr/mesa-qr').then(
            (m) => m.MesaQrComponent,
          ),
        canActivate: [configuracionGuard],
      },
      {
        path: 'categorias',
        loadComponent: () =>
          import('../features/categorias/pages/categoria/categoria').then(
            (m) => m.CategoriaComponent,
          ),
        canActivate: [configuracionGuard],
      },
      {
        path: 'productos',
        loadComponent: () =>
          import('../features/productos/pages/producto/producto').then(
            (m) => m.ProductoComponent,
          ),
        canActivate: [configuracionGuard],
      },
      {
        path: 'pedidos',
        loadComponent: () =>
          import('../features/pedidos/pages/pedido/pedido').then(
            (m) => m.PedidoComponent,
          ),
        canActivate: [configuracionGuard],
      },
      {
        path: 'pagos',
        loadComponent: () =>
          import('../features/pagos/pages/pago/pago').then(
            (m) => m.PagoComponent,
          ),
        canActivate: [configuracionGuard],
      },
      {
        path: 'reservas',
        loadComponent: () =>
          import('../features/reservas/pages/reserva/reserva').then(
            (m) => m.ReservaComponent,
          ),
        canActivate: [configuracionGuard],
      },
      {
        path: 'ventas',
        loadComponent: () =>
          import('../features/ventas/pages/venta/venta').then(
            (m) => m.VentaComponent,
          ),
        canActivate: [configuracionGuard],
      },
      {
        path: 'cocina',
        loadComponent: () =>
          import('../features/cocina/pages/cocina/cocina').then(
            (m) => m.CocinaComponent,
          ),
        canActivate: [configuracionGuard],
      },
      {
        path: 'contabilidad',
        loadComponent: () =>
          import('../features/contabilidad/pages/contabilidad/contabilidad').then(
            (m) => m.ContabilidadComponent,
          ),
        canActivate: [configuracionGuard],
      },
      {
        path: 'delivery',
        loadComponent: () =>
          import('../features/delivery/pages/delivery/delivery').then(
            (m) => m.DeliveryComponent,
          ),
        canActivate: [configuracionGuard],
      },
      {
        path: 'facturacion',
        loadComponent: () =>
          import('../features/facturacion/pages/facturacion/facturacion').then(
            (m) => m.FacturacionComponent,
          ),
        canActivate: [configuracionGuard],
      },
      {
        path: 'reportes',
        loadComponent: () =>
          import('../features/reportes/pages/reportes/reportes').then(
            (m) => m.ReportesComponent,
          ),
        canActivate: [configuracionGuard],
      },
      {
        path: 'notificaciones',
        loadComponent: () =>
          import('../features/notificaciones/pages/notificacion/notificacion').then(
            (m) => m.NotificacionComponent,
          ),
      },
    ],
  },
];
