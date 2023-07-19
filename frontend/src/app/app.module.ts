import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {RouterModule} from '@angular/router';
import {TableModule} from 'primeng/table';
import {DialogModule} from 'primeng/dialog'
import {ButtonModule} from 'primeng/button'
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from '@angular/material/paginator';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {InplaceModule} from 'primeng/inplace';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    InplaceModule,
    FormsModule,
    ReactiveFormsModule,
    DialogModule,
    MatPaginatorModule,
    MatTableModule,
    RouterModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ButtonModule,
    BrowserModule,
    AppRoutingModule,
    TableModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
