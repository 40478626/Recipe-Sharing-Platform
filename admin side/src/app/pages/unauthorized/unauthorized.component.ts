import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';


@Component({
    selector: 'unauthorized-cmp',
    moduleId: module.id,
    templateUrl: 'unauthorized.component.html'
})

export class UnauthorizedComponent {
    constructor(private router: Router) {}

goToLogin() {
  this.router.navigate(['/login']);
}
   
}
