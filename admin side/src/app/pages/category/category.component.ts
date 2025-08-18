import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'category-cmp',
    templateUrl: 'category.component.html'
})
export class CategoryComponent implements OnInit {
    categories: any[] = [];
    categoryName: string = ''; // Store the category name to update
    selectedCategoryId: number | null = null; // Store the selected category ID

    constructor(private http: HttpClient,private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.getCategories();
    }

    // Open modal
  open(content: any, categoryId: number, categoryName: string) {
    this.selectedCategoryId = categoryId; // Set the selected category ID
    this.categoryName = categoryName; // Set the category name in the input
    this.modalService.open(content, { centered: true, size: 'lg' }); // Ensure modal size is large enough to interact with
  }


    getCategories() {
        const token = sessionStorage.getItem('authToken'); // Retrieve token from sessionStorage
        if (!token) {
             this.router.navigate(['/login']);
            console.error("No token found!");
            return;
        }

        const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
        

        this.http.post<any>('http://localhost:8000/recipe-sharing-platform/api/get-category', {}, { headers })
            .subscribe(
                response => {
                    if (response.success) {
                        this.categories = response.result; // Store API result in categories array
                    } else {
                        console.error("API returned an error:", response.message);
                    }
                },
                error => {
                    console.error("Error fetching categories:", error);
                }
            );
    }
    // Update Category API call
  updateCategory(modal: any) {
    if (this.selectedCategoryId === null) {
      console.error("No category selected for update!");
      return;
    }

    const token = sessionStorage.getItem('authToken');
    if (!token) {
      this.router.navigate(['/login']);
      console.error("No token found!");
      return;
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    const updatedCategory = { name: this.categoryName };

    this.http.post<any>(`http://localhost:8000/recipe-sharing-platform/api/update-category/${this.selectedCategoryId}`, updatedCategory, { headers })
      .subscribe(
        response => {
          if (response.success) {
            console.log('Category updated successfully');
            modal.dismiss(); // Close the modal after updating
            this.getCategories(); // Refresh categories
          } else {
            console.error("Error updating category:", response.message);
          }
        },
        error => {
          console.error("Error updating category:", error);
        }
      );
  }
     //Delete Category API call
  deleteCategory(categoryId: number) {
    const token = sessionStorage.getItem('authToken');
    if (!token) {
      this.router.navigate(['/login']);
      console.error("No token found!");
      return;
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    console.log(token)

    this.http.post<any>(`http://localhost:8000/recipe-sharing-platform/api/delete-category/${categoryId}`,{}, { headers })
      .subscribe(
        response => {
          if (response.success) {
            console.log('Category deleted successfully');
            this.getCategories(); // Refresh the list after deletion
          } else {
            console.error('Error deleting category:', response.message);
          }
        },
        error => {
          console.error('Error deleting category:', error);
        }
      );
  }
}