import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
@Component({
    selector: 'comment-cmp',
    moduleId: module.id,
    templateUrl: 'comment.component.html'
})

export class CommentComponent implements OnInit{
  recipes: any[] = [];
  selectedRecipeId: number | null = null;
  comments: any[] = [];
  selectedRecipeName: string = '';

    constructor(private http: HttpClient,private router: Router) {}

    ngOnInit() {
      this.fetchRecipes();
    }


     // Fetch the list of recipes for the dropdown
  fetchRecipes() {
    const token = sessionStorage.getItem('authToken');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    this.http.post<any>('http://localhost:8000/recipe-sharing-platform/api/get-recipe',{}, { headers })
      .subscribe(response => {
        if (response.success) {
          this.recipes = response.result;
        }
      }, error => {
        console.error('Error fetching recipes:', error);
      });
  }

  // Select a recipe from the dropdown
  selectRecipe(recipe: any) {
    this.selectedRecipeId = recipe.id;
    this.selectedRecipeName = recipe.name;
    this.fetchComments();
  }

  // Fetch comments based on the selected recipe
  fetchComments() {
    if (!this.selectedRecipeId) return;

    const token = sessionStorage.getItem('authToken');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    this.http.post<any>(`http://localhost:8000/recipe-sharing-platform/api/get-comment/${this.selectedRecipeId}`,{}, { headers })
      .subscribe(response => {
        if (response.success) {
          this.comments = response.result;
        } else {
          this.comments = [];
        }
      }, error => {
        console.error('Error fetching comments:', error);
      });
  }
     //Delete Collection API call
     deleteComment(commentId: number) {
      const token = sessionStorage.getItem('authToken');
      if (!token) {
        this.router.navigate(['/login']);
        console.error("No token found!");
        return;
      }
  
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      console.log(token)
  
      this.http.post<any>(`http://localhost:8000/recipe-sharing-platform/api/delete-comment/${commentId}`,{}, { headers })
        .subscribe(
          response => {
            if (response.success) {
              console.log('Comment deleted successfully');
              this.fetchComments(); // Refresh the list after deletion
            } else {
              console.error('Error deleting comment:', response.message);
            }
          },
          error => {
            console.error('Error deleting comment:', error);
          }
        );
    }
}