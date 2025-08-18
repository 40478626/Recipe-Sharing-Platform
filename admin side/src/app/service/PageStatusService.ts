import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PageStatusService {
  private isUnauthorized = false;

  setUnauthorizedStatus(status: boolean): void {
    this.isUnauthorized = status;
  }

  getUnauthorizedStatus(): boolean {
    return this.isUnauthorized;
  }
}
