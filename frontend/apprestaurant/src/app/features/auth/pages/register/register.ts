import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../../core/services/auth';
import { RegisterRequest } from '../../../../core/models/register-request';
import { RolService } from '../../../../core/services/usuario.service';
import { RoleModels } from '../../../../core/models/role.models';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './register.html',
  styleUrl: './register.scss',
})
export class RegisterComponent implements OnInit {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);
  private rolService = inject(RolService);

  loading = false;
  errorMsg = '';
  roles: RoleModels[] = [];

  registerForm = this.fb.nonNullable.group({
    username: ['', [Validators.required, Validators.minLength(3)]],
    nombres: ['', [Validators.required, Validators.minLength(3)]],
    apellidos: ['', [Validators.required, Validators.minLength(3)]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
    telefono: [''],
    rolNombre: ['', [Validators.required]],
  });

  ngOnInit() {
    this.rolService.findAll().subscribe(data => this.roles = data);
  }

  onSubmit(): void {
    if (this.registerForm.invalid) {
      this.registerForm.markAllAsTouched();
      return;
    }

    const data: RegisterRequest = {
      username: this.registerForm.getRawValue().username,
      nombres: this.registerForm.getRawValue().nombres,
      apellidos: this.registerForm.getRawValue().apellidos,
      email: this.registerForm.getRawValue().email,
      password: this.registerForm.getRawValue().password,
      telefono: this.registerForm.getRawValue().telefono || undefined,
      rolNombre: this.registerForm.getRawValue().rolNombre,
    };

    this.loading = true;
    this.errorMsg = '';

    this.authService.register(data).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigate(['/sistema/onboarding']);
      },
      error: (err) => {
        this.loading = false;
        this.errorMsg = err?.error?.mensaje || 'Error al registrarse';
      },
    });
  }
}
