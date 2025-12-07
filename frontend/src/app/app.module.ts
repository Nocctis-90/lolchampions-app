import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ItemListComponent } from './components/item-list/item-list.component';
import { ChampionListComponent } from './components/champion-list/champion-list.component';
import { ChampionDetailComponent } from './components/champion-detail/champion-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    ItemListComponent,
    ChampionListComponent,
    ChampionDetailComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
