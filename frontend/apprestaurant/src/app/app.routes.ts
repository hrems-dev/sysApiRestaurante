import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth-guard';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'menu',
  },
  {
    path: 'auth',
    loadChildren: () =>
      import('./features/auth/auth.routes').then((m) => m.AUTH_ROUTES),
  },
  {
    path: 'menu',
    loadComponent: () =>
      import('./features/menu-publico/pages/menu-publico/menu-publico').then(
        (m) => m.MenuPublicoComponent,
      ),
  },
  {
    path: 'sistema',
    canActivate: [authGuard],
    loadChildren: () =>
      import('./layouts/main-layout.routes').then((m) => m.MAIN_ROUTES),
  },
  {
    path: '**',
    redirectTo: 'menu',
  },
];
