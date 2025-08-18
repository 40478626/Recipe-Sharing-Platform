import { Routes } from '@angular/router';

import { DietaryPreferencesComponent } from '../../pages/dietaryPreferences/dietaryPreferences.component';
import { CollectionComponent } from '../../pages/collection/collection.component';

import { CategoryComponent } from '../../pages/category/category.component';
import { RecipeComponent } from '../../pages/recipe/recipe.component';
import { DashboardComponent } from 'app/pages/dashboard/dashboard.component';
import { AuthGuard } from '../../guards/auth.guard'; 
import { LoginComponent } from 'app/pages/login/login.component';
import { CommentComponent } from 'app/pages/comment/comment.component';
import { ImageComponent } from 'app/pages/image/image.component';
import { UnauthorizedComponent } from 'app/pages/unauthorized/unauthorized.component';

export const AdminLayoutRoutes: Routes = [
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    { path: 'login', component: LoginComponent ,canActivate: [AuthGuard] },
    { path: 'unauthorized', component: UnauthorizedComponent  },
    { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
    { path: 'dietaryPreferences',      component: DietaryPreferencesComponent,canActivate: [AuthGuard] },
    { path: 'collection',           component: CollectionComponent ,canActivate: [AuthGuard]},
    { path: 'image',          component: ImageComponent  ,canActivate: [AuthGuard]},
    { path: 'category',          component: CategoryComponent  ,canActivate: [AuthGuard]},
    { path: 'recipe',           component: RecipeComponent ,canActivate: [AuthGuard] },
    { path: 'comment',           component: CommentComponent ,canActivate: [AuthGuard] },

];
