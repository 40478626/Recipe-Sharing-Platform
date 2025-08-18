import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
@Component({
    selector: 'collection-cmp',
    moduleId: module.id,
    templateUrl: 'collection.component.html'
})

export class CollectionComponent implements OnInit{
    collections: any[] = [];
    collectionName: string = ''; // Store the collection name to update
    selectedCollectionId: number | null = null; // Store the selected collection ID

    constructor(private http: HttpClient,private router: Router) {}

    ngOnInit() {
        this.getCollections();
    }


    getCollections() {
        const token = sessionStorage.getItem('authToken'); // Retrieve token from sessionStorage
        if (!token) {
             this.router.navigate(['/login']);
            console.error("No token found!");
            return;
        }

        const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
        

        this.http.post<any>('http://localhost:8000/recipe-sharing-platform/api/get-collection', {}, { headers })
            .subscribe(
                response => {
                    if (response.success) {
                        this.collections = response.result; // Store API result in collections array
                    } else {
                        console.error("API returned an error:", response.message);
                    }
                },
                error => {
                    console.error("Error fetching collections:", error);
                }
            );
    }
     //Delete Collection API call
  deleteCategory(collectionId: number) {
    const token = sessionStorage.getItem('authToken');
    if (!token) {
      this.router.navigate(['/login']);
      console.error("No token found!");
      return;
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    console.log(token)

    this.http.post<any>(`http://localhost:8000/recipe-sharing-platform/api/delete-collection/${collectionId}`,{}, { headers })
      .subscribe(
        response => {
          if (response.success) {
            console.log('Collection deleted successfully');
            this.getCollections(); // Refresh the list after deletion
          } else {
            console.error('Error deleting collection:', response.message);
          }
        },
        error => {
          console.error('Error deleting collection:', error);
        }
      );
  }
}
