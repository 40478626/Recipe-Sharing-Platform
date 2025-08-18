import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'dietaryPreferences-cmp',
    moduleId: module.id,
    templateUrl: 'dietaryPreferences.component.html'
})

export class DietaryPreferencesComponent implements OnInit{

  dietaryPreferences: any[] = [];
  dietaryName: string = ''; // Store the dietary name to update
    selectedDietaryId: number | null = null; // Store the selected dietary ID

    constructor(private http: HttpClient,private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.getDietary();
    }

    // Open modal
  open(content: any, categoryId: number, categoryName: string) {
    this.selectedDietaryId = categoryId; // Set the selected dietary ID
    this.dietaryName = categoryName; // Set the dietary name in the input
    this.modalService.open(content, { centered: true, size: 'lg' }); // Ensure modal size is large enough to interact with
  }


    getDietary() {
        const token = sessionStorage.getItem('authToken'); // Retrieve token from sessionStorage
        if (!token) {
             this.router.navigate(['/login']);
            console.error("No token found!");
            return;
        }

        const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
        

        this.http.post<any>('http://localhost:8000/recipe-sharing-platform/api/get-dietary', {}, { headers })
            .subscribe(
                response => {
                    if (response.success) {
                        this.dietaryPreferences = response.result; // Store API result in dietaryPreferences array
                    } else {
                        console.error("API returned an error:", response.message);
                    }
                },
                error => {
                    console.error("Error fetching dietaryPreferences:", error);
                }
            );
    }
    // Update Dietary API call
  updateDietary(modal: any) {
    if (this.selectedDietaryId === null) {
      console.error("No dietary selected for update!");
      return;
    }

    const token = sessionStorage.getItem('authToken');
    if (!token) {
      this.router.navigate(['/login']);
      console.error("No token found!");
      return;
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    const updatedDietary = { name: this.dietaryName };

    this.http.post<any>(`http://localhost:8000/recipe-sharing-platform/api/update-dietary/${this.selectedDietaryId}`, updatedDietary, { headers })
      .subscribe(
        response => {
          if (response.success) {
            console.log('Dietary updated successfully');
            modal.dismiss(); // Close the modal after updating
            this.getDietary(); // Refresh dietaryPreferences
          } else {
            console.error("Error updating dietary:", response.message);
          }
        },
        error => {
          console.error("Error updating dietary:", error);
        }
      );
  }
     //Delete Dietary API call
  deleteDietary(categoryId: number) {
    const token = sessionStorage.getItem('authToken');
    if (!token) {
      this.router.navigate(['/login']);
      console.error("No token found!");
      return;
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    console.log(token)

    this.http.post<any>(`http://localhost:8000/recipe-sharing-platform/api/delete-dietary/${categoryId}`,{}, { headers })
      .subscribe(
        response => {
          if (response.success) {
            console.log('Dietary deleted successfully');
            this.getDietary(); // Refresh the list after deletion
          } else {
            console.error('Error deleting dietary:', response.message);
          }
        },
        error => {
          console.error('Error deleting dietary:', error);
        }
      );
  }
}