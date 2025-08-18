import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent{
  isUnauthorizedPage = false;
  constructor(private router: Router) {
    this.router.events.subscribe(() => {
      this.isUnauthorizedPage = this.router.url.includes('/unauthorized');
    });
  }

get showLayout(): boolean {
  return !['/unauthorized', '/login'].includes(this.router.url);
}

}
