import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ItemListComponent } from './components/item-list/item-list.component';
import { ChampionListComponent } from './components/champion-list/champion-list.component';
import { ChampionDetailComponent } from './components/champion-detail/champion-detail.component';
import { HomeComponent } from './components/home/home.component';
import { RotationComponent } from './components/rotation/rotation.component';
import { SeasonDetailComponent } from './components/season-detail/season-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    ItemListComponent,
    ChampionListComponent,
    ChampionDetailComponent,
    HomeComponent,
    RotationComponent,
    SeasonDetailComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
