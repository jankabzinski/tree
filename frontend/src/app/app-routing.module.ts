import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {TreeComponent} from "./tree/tree.component";


const routes: Routes = [
  {
    path:'nodes',
    component: TreeComponent
  },
  {
    path: '**',
    redirectTo: 'nodes'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
