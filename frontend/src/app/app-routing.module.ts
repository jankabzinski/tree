import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';


const routes: Routes = [

  {
    path: '',
    redirectTo: 'nodes',
    pathMatch: 'full'
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
