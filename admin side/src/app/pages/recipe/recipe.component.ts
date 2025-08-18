import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'recipe-cmp',
  templateUrl: './recipe.component.html'
})
export class RecipeComponent implements OnInit {
  recipes: any[] = [];
  categories: any[] = [];
  dietaryPreferences: any[] = [];
  selectedRecipeId: number | null = null;

  recipe: {
    id: any;
    name: any;
    category: any;
    imageId: any;
    description: any;
    cookingTime: any;
    ingredients: any;
    dietaryPreferences: any;
  } = {
    id: null,
    name: '',
    category: '',
    imageId: '',
    description: '',
    cookingTime: '',
    ingredients: '',
    dietaryPreferences: ''
  };

  imageUrls: { [recipeId: number]: string } = {};
  selectedImageFile: File | null = null;

  constructor(private http: HttpClient, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.getRecipes();
    this.fetchCategories();
    this.fetchDietaryPreferences();
    this.getAllImages();
    // this.loadImageh(452);
  }
  // loadImageh(imageId: number): void {
  //   const token = sessionStorage.getItem('token'); // adjust if stored elsewhere

  //   const headers = new HttpHeaders({
  //     Authorization: `Bearer ${token}`,
  //   });

  //   this.http.post(`http://localhost:8000/recipe-sharing-platform/api/get-images/${imageId}`, {}, {
  //     headers: headers,
  //     responseType: 'blob'
  //   }).subscribe(blob => {
  //     const imageUrl = URL.createObjectURL(blob);
  //     this.imageUrls[imageId] = imageUrl;
  //   }, error => {
  //     console.error('Failed to load image', error);
  //   });
  // }

  getRecipes() {
    const token = sessionStorage.getItem('authToken');
    if (!token) return this.router.navigate(['/login']);

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    this.http.post<any>('http://localhost:8000/recipe-sharing-platform/api/get-recipe', {}, { headers })
      .subscribe(response => {
        if (response.success) {
          this.recipes = response.result;
          this.recipes.forEach(recipe => {
            if (recipe.imageId) {
              this.loadImage(recipe.id, recipe.imageId);
            }
          });
        }
      });
  }

  getAllImages() {
    const token = sessionStorage.getItem('authToken');
    if (!token) return;
  
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
  
    this.http.post<any>('http://localhost:8000/recipe-sharing-platform/api/get-all-images', {}, { headers })
      .subscribe(images => {
        console.log('Images received:', images);  // Check if images are received
        if (!Array.isArray(images)) return;
  
        images.forEach((img: any) => {
          const recipeId = parseInt(img.recipeId, 10);
          const imageId = img.id;
          if (recipeId) {
            console.log('Loading image for recipeId:', recipeId, 'with imageId:', imageId);  // Log image loading attempt
            this.loadImage(recipeId, imageId);
            
          }
        });
      });
  }
  
  // loadImage(recipeId: number, imageId: number) {
  //   console.log('Fetching image for imageId:', imageId);  // Log before calling API
  //   const token = sessionStorage.getItem('authToken');
  //   if (!token) return;
  
  //   const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
  
  //   this.http.post(`http://localhost:8000/recipe-sharing-platform/api/get-images/${imageId}`, {}, {
  //     headers: headers,
  //     responseType: 'blob'
  //   }).subscribe(blob => {
  //     const reader = new FileReader();
  //     reader.readAsDataURL(blob);
  //     reader.onloadend = () => {
  //       const base64data = reader.result as string;
  //       this.imageUrls[imageId] = base64data;
  //     };
  //   });    
    
  // }
  
  loadImage(recipeId: number, imageId: number) {
    const token = sessionStorage.getItem('authToken');
    if (!token) return;
  
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
  
    this.http.post(`http://localhost:8000/recipe-sharing-platform/api/get-images/${imageId}`, {}, {
      headers,
      responseType: 'blob'
    }).subscribe(blob => {
      const reader = new FileReader();
      reader.readAsDataURL(blob);
      reader.onloadend = () => {
        const base64data = reader.result as string;
        
        // 👇 FIX: Save the image based on recipeId not imageId
        if (!this.imageUrls[recipeId]) {
          this.imageUrls[recipeId] = base64data;
        }
      };
    }, err => {
      console.warn(`Could not load image for recipeId=${recipeId}, imageId=${imageId}`, err);
    });
  }
  
  fetchCategories() {
    const token = sessionStorage.getItem('authToken');
    if (!token) return this.router.navigate(['/login']);

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    this.http.post<any>('http://localhost:8000/recipe-sharing-platform/api/get-category', {}, { headers })
      .subscribe(response => {
        if (response.success) this.categories = response.result.filter(c => c.name);
      });
  }

  fetchDietaryPreferences() {
    const token = sessionStorage.getItem('authToken');
    if (!token) return this.router.navigate(['/login']);

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    this.http.post<any>('http://localhost:8000/recipe-sharing-platform/api/get-dietary', {}, { headers })
      .subscribe(response => {
        if (response.success) this.dietaryPreferences = response.result.filter(d => d.name);
      });
  }

  openRecipeModal(content: any, recipe: any) {
    this.selectedRecipeId = recipe.id;
    this.recipe = { ...recipe };
    this.modalService.open(content, { centered: true, size: 'lg' });
  }

  onImageSelected(event: any) {
    if (event.target.files && event.target.files.length > 0) {
      this.selectedImageFile = event.target.files[0];
    }
  }

  updateRecipe(modal: any) {
    const token = sessionStorage.getItem('authToken');
    if (!token || !this.selectedRecipeId) return this.router.navigate(['/login']);

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    if (this.selectedImageFile) {
      const formData = new FormData();
      formData.append('image', this.selectedImageFile);

      this.http.post<any>('http://localhost:8000/recipe-sharing-platform/api/upload-image', formData, { headers })
        .subscribe(response => {
          if (response.success && response.imageId) {
            this.recipe.imageId = response.imageId;
            this.proceedWithUpdate(modal, headers);
          }
        });
    } else {
      this.proceedWithUpdate(modal, headers);
    }
  }

  proceedWithUpdate(modal: any, headers: HttpHeaders) {
    const updatedRecipe = {
      name: this.recipe.name,
      category: this.recipe.category,
      imageId: this.recipe.imageId || '',
      description: this.recipe.description,
      cookingTime: this.recipe.cookingTime,
      ingredients: this.recipe.ingredients,
      dietaryPreferences: this.recipe.dietaryPreferences
    };

    this.http.post<any>(`http://localhost:8000/recipe-sharing-platform/api/update-recipe/${this.selectedRecipeId}`, updatedRecipe, { headers })
      .subscribe(response => {
        if (response.success) {
          this.getRecipes();
          modal.dismiss();
        }
      });
  }

  deleteRecipe(recipeId: number) {
    const token = sessionStorage.getItem('authToken');
    if (!token) return this.router.navigate(['/login']);

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    this.http.post<any>(`http://localhost:8000/recipe-sharing-platform/api/delete-recipe/${recipeId}`, {}, { headers })
      .subscribe(response => {
        if (response.success) this.getRecipes();
      });
  }
}
