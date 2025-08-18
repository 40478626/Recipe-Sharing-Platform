import { Component, OnInit } from '@angular/core';
import Chart from 'chart.js';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'dashboard-cmp',
    moduleId: module.id,
    templateUrl: 'dashboard.component.html'
})

export class DashboardComponent implements OnInit {
  newCategory = { name: '' }; // Model for new category
  newRecipe: any = {
    name: '',
    category: '',
    imageId: '',
    description: '',
    cookingTime: '',
    ingredients: '',
    dietaryPreferences: ''
  };
  categories: any[] = [];
  dietaryPreferences: any[] = [];
  newDietary = { name: '' }; // Model for new dietary
  uploadData = {
    recipeId: ''
  };
  
  selectedFile: File | null = null;


  constructor(private router: Router, private http: HttpClient, private modalService: NgbModal) {}

  ngOnInit() {

    const token = sessionStorage.getItem('authToken');
    console.log('Dashboard - Retrieved Token:', token); // Debugging
  
    if (!token) {
        console.warn("Dashboard: No authToken found. Redirecting to login.");
        this.router.navigate(['/login']);
        return; // Ensure no further processing happens if no token
    }
  this.fetchCategories();
    this.fetchDietaryPreferences();

    setTimeout(() => {
      const sidebar = document.getElementById('sidebar');
      if (sidebar) {
        sidebar.style.display = 'block';
      }
    }, 0);

    // this.fetchCategories();
    // this.fetchDietaryPreferences();

    console.log('Dashboard loaded');
  }

  // Open Create Category Modal
  openCreateCategoryModal(content: any) {
    this.newCategory.name = ''; // Reset input field
    this.modalService.open(content, { centered: true });
  }

  createCategory(modal: any) {
    // Check if category name is empty
    if (!this.newCategory.name.trim()) {
      alert("Category name cannot be empty!");  // Show validation message
      return;  // Prevent API call if name is empty
    }
  
    const token = sessionStorage.getItem('authToken');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
  
    this.http.post('http://localhost:8000/recipe-sharing-platform/api/create-category', this.newCategory, { headers })
      .subscribe(
        response => {
          console.log('Category created successfully:', response);
          alert("Category created successfully!");
          modal.dismiss();
        },
        error => {
          console.error("Error creating category:", error);
          alert("Failed to create category.");
        }
      );
  }  
    // Fetch categories from API with token
    fetchCategories() {
      const token = sessionStorage.getItem('authToken');
      if (!token) {
        this.router.navigate(['/login']);
        console.error("No token found!");
        return;
      }

      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

      this.http.post<any>('http://localhost:8000/recipe-sharing-platform/api/get-category', {}, { headers })
      .subscribe(response => {
          if (response.success) {
            this.categories = response.result.filter(category => category.name); // Remove empty names
            console.log("Category ok ")
            console.log(this.categories)
          }
        }, error => {
          console.error('Error fetching categories:', error);
        });
    }

    // Fetch dietary preferences from API with token
    fetchDietaryPreferences() {
      const token = sessionStorage.getItem('authToken');
      if (!token) {
        this.router.navigate(['/login']);
        console.error("No token found!");
        return;
      }

      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

      this.http.post<any>('http://localhost:8000/recipe-sharing-platform/api/get-dietary',{}, { headers })
        .subscribe(response => {
          if (response.success) {
            this.dietaryPreferences = response.result.filter(dietary => dietary.name); // Remove empty names
            console.log("Dietary ok ")
            console.log(this.dietaryPreferences)
          }
        }, error => {
          console.error('Error fetching dietary preferences:', error);
        });
    }
    // Open Create Recipe Modal
    openCreateRecipeModal(content: any) {
      this.newRecipe = {
        name: '',
        category: '',
        imageId: '',
        // userId: '85d7864a-4035-48cf-af75-bf92e2afba0a',
        description: '',
        cookingTime: '',
        ingredients: '',
        dietaryPreferences: ''
      };
      this.modalService.open(content, { centered: true });
    }

    // Create Recipe
    createRecipe(modal: any) {
      if (!this.newRecipe.name.trim() || !this.newRecipe.category || !this.newRecipe.dietaryPreferences) {
        alert('All fields are required!');
        return;
      }

      const token = sessionStorage.getItem('authToken');
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

      const recipeData = {
        name: this.newRecipe.name,
        category: this.newRecipe.category,
        imageId: this.newRecipe.imageId, // Handle file upload separately
        // userId: this.newRecipe.userId,
        description: this.newRecipe.description,
        cookingTime: this.newRecipe.cookingTime,
        ingredients: this.newRecipe.ingredients,
        dietaryPreferences: this.newRecipe.dietaryPreferences
      };

      this.http.post('http://localhost:8000/recipe-sharing-platform/api/create-recipe', recipeData, { headers })
        .subscribe(
          response => {
            console.log('Recipe created successfully:', response);
            alert('Recipe created successfully!');
            modal.dismiss();
          },
          error => {
            console.error('Error creating recipe:', error);
            alert('Failed to create recipe.');
          }
        );
    }

    // Open Create Dietary Modal
  openCreateDietaryModal(content: any) {
    this.newDietary.name = ''; // Reset input field
    this.modalService.open(content, { centered: true });
  }

  createDietary(modal: any) {
    // Check if Dietary name is empty
    if (!this.newDietary.name.trim()) {
      alert("Dietary name cannot be empty!");  // Show validation message
      return;  // Prevent API call if name is empty
    }
  
    const token = sessionStorage.getItem('authToken');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
  
    this.http.post('http://localhost:8000/recipe-sharing-platform/api/create-dietary', this.newDietary, { headers })
      .subscribe(
        response => {
          console.log('Dietary created successfully:', response);
          alert("Dietary created successfully!");
          modal.dismiss();
        },
        error => {
          console.error("Error creating dietary:", error);
          alert("Failed to create dietary.");
        }
      );
  }  

  // Open the image upload modal
openUploadImageModal(content: any) {
  this.uploadData.recipeId = '';
  this.selectedFile = null;
  this.modalService.open(content, { centered: true });
}

// File selection handler
onFileSelected(event: any) {
  this.selectedFile = event.target.files[0];
}

uploadImage(modal: any) {
  if (!this.selectedFile || !this.uploadData.recipeId) return;

  const token = sessionStorage.getItem('authToken');

  const headers = new HttpHeaders({
    'Authorization': `Bearer ${token}`
  });

  const formData = new FormData();

  // Wrap metadata as JSON Blob so Spring can deserialize it
  const metadata = {
    recipeId: this.uploadData.recipeId
  };

  const metadataBlob = new Blob(
    [JSON.stringify(metadata)],
    { type: 'application/json' }
  );

  formData.append('metadata', metadataBlob);
  formData.append('file', this.selectedFile as Blob);

  this.http.post('http://localhost:8000/recipe-sharing-platform/api/upload', formData, { headers })
    .subscribe(
      (res) => {
        console.log('Image uploaded successfully:', res);
        alert('Image uploaded successfully!');
        modal.dismiss();
      },
      (err) => {
        console.error('Upload failed:', err);
        alert('Image upload failed.');
      }
    );
}
      
}
