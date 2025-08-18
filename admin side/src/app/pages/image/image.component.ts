import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'image-cmp',
  templateUrl: 'image.component.html'
})
export class ImageComponent implements OnInit {
  recipes: any[] = [];
  images: any[] = [];
  filteredImages: any[] = [];
  selectedRecipeId: number | null = null;
  selectedRecipeName: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit() {
    this.fetchRecipes();
    this.fetchImages();
  }

  fetchRecipes() {
    const token = sessionStorage.getItem('authToken');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    this.http.post<any>('http://localhost:8000/recipe-sharing-platform/api/get-recipe', {}, { headers })
      .subscribe(response => {
        if (response.success) {
          this.recipes = response.result;
        }
      });
  }

  fetchImages() {
    const token = sessionStorage.getItem('authToken');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
  
    this.http.post<any>('http://localhost:8000/recipe-sharing-platform/api/get-all-images', {}, { headers })
      .subscribe(async response => {
        // response = [{ id: 1, recipeId: 10 }, ...]
        const imageList = await Promise.all(
          response.map(async (img: any) => {
            const blob = await this.fetchImageBlob(img.id, headers);
            const base64 = await this.blobToBase64(blob);
            return {
              id: img.id,
              recipeId: img.recipeId,
              base64Image: base64
            };
          })
        );
  
        this.images = imageList;
        this.filterImages();
      });
  }
  
  fetchImageBlob(imageId: number, headers: HttpHeaders): Promise<Blob> {
    const url = `http://localhost:8000/recipe-sharing-platform/api/get-images/${imageId}`;
    return this.http.post(url,{}, { headers, responseType: 'blob' }).toPromise();
  }
  
  blobToBase64(blob: Blob): Promise<string> {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(blob);
      reader.onloadend = () => resolve(reader.result as string);
      reader.onerror = reject;
    });
  }
  
  
  // For selecting recipe
  selectRecipe(recipe: any) {
    this.selectedRecipeId = recipe.id;
    this.selectedRecipeName = recipe.name;
    this.filterImages();
  }

  // Filtering images based on selected recipe
  filterImages() {
    if (this.selectedRecipeId) {
      this.filteredImages = this.images.filter(img => img.recipeId == this.selectedRecipeId);
    } else {
      this.filteredImages = this.images;
    }
  }

  getRecipeName(recipeId: number) {
    const recipe = this.recipes.find(r => r.id == recipeId);
    return recipe ? recipe.name : 'Unknown';
  }

  getImageUrl(fileName: string): string {
    // Replace with your actual backend logic if you serve images
    return `http://localhost:8000/recipe-sharing-platform/uploads/images/${fileName}`;
  }

  deleteImage(imageId: number) {
    const token = sessionStorage.getItem('authToken');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    this.http.post<any>(`http://localhost:8000/recipe-sharing-platform/api/delete-recipe/${imageId}`, {}, { headers })
      .subscribe(response => {
        if (response.success) {
          alert('Image deleted successfully!');
          this.fetchImages(); // Refresh list
        } else {
          alert('Failed to delete image');
        }
      });
  }
}
