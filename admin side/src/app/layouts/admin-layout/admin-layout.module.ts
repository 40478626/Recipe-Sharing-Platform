import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { AdminLayoutRoutes } from './admin-layout.routing';

import { DietaryPreferencesComponent } from '../../pages/dietaryPreferences/dietaryPreferences.component';
import { CollectionComponent } from '../../pages/collection/collection.component';
import { RecipeComponent } from '../../pages/recipe/recipe.component';
import { CategoryComponent } from 'app/pages/category/category.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CommentComponent } from 'app/pages/comment/comment.component';
import { ImageComponent } from 'app/pages/image/image.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(AdminLayoutRoutes),
    FormsModule,
    NgbModule, 
  ],
  declarations: [
    DietaryPreferencesComponent,
    CollectionComponent,
    ImageComponent,
    CategoryComponent,
    RecipeComponent,
    CommentComponent
  ]
})

export class AdminLayoutModule {}
