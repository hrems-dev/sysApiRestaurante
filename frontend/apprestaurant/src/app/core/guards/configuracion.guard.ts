import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { map } from 'rxjs';
import { OnboardingService } from '../services/onboarding.service';

export const configuracionGuard = () => {
  const router = inject(Router);
  const onboardingService = inject(OnboardingService);

  return onboardingService.obtenerEstadoModulos().pipe(
    map((estado) => {
      if (estado.restauranteRegistrado) {
        return true;
      }
      return router.parseUrl('/sistema/onboarding');
    }),
  );
};
