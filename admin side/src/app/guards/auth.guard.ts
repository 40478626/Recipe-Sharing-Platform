// auth.guard.ts
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { PageStatusService } from '../service/PageStatusService';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router, private pageStatusService: PageStatusService) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const token = sessionStorage.getItem('authToken');

    if (token) {
      try {
        const decodedToken: any = jwtDecode(token);
        const roles = decodedToken?.resource_access?.['recipe-app-client']?.roles || [];

        if (roles.includes('ADMIN')) {
          return true;
        } else {
          this.pageStatusService.setUnauthorizedStatus(true);
          this.router.navigate(['/unauthorized']);
          return false;
        }
      } catch (err) {
        console.error('Error decoding token:', err);
        this.router.navigate(['/login']);
        return false;
      }
    }

    this.pageStatusService.setUnauthorizedStatus(true);
    this.router.navigate(['/login']);
    return false;
  }
}
