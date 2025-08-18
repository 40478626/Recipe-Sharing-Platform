import { Component, OnInit, ElementRef, Renderer2 } from '@angular/core';
import { Router } from '@angular/router';

export interface RouteInfo {
    path: string;
    title: string;
    icon: string;
    class: string;
}

export const ROUTES: RouteInfo[] = [
    { path: '/dashboard', title: 'Admin Portal', icon: 'nc-bank', class: '' },
    { path: '/recipe', title: 'Recipe', icon: 'nc-paper', class: '' },
    { path: '/collection', title: 'Collection', icon: 'nc-vector', class: '' },
    { path: '/image', title: 'Image', icon: 'nc-tile-56', class: '' },
    { path: '/category', title: 'Category', icon: 'nc-layout-11', class: '' },
    { path: '/dietaryPreferences', title: 'Dietary Preferences', icon: 'nc-tile-56', class: '' },
    { path: '/comment', title: 'Comments', icon: 'nc-chat-33', class: '' },
];

@Component({
    moduleId: module.id,
    selector: 'sidebar-cmp',
    templateUrl: 'sidebar.component.html',
})
export class SidebarComponent implements OnInit {
    public menuItems: any[];

    constructor(private el: ElementRef, private renderer: Renderer2,public router: Router) {}

    ngOnInit() {
        this.menuItems = ROUTES.filter(menuItem => menuItem);

        // Ensure the sidebar is visible once Angular initializes the component
        const sidebar = this.el.nativeElement.querySelector('#sidebar');
        if (sidebar) {
            this.renderer.setStyle(sidebar, 'display', 'block');
        }
    }
    shouldShow(): boolean {
        return this.router.url !== '/unauthorized';
      }
}
