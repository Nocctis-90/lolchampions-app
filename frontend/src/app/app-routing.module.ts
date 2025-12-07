import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ItemListComponent } from './components/item-list/item-list.component';

import { ChampionListComponent } from './components/champion-list/champion-list.component';
import { ChampionDetailComponent } from './components/champion-detail/champion-detail.component';

import { HomeComponent } from './components/home/home.component';
import { RotationComponent } from './components/rotation/rotation.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'items', component: ItemListComponent },
  { path: 'champions', component: ChampionListComponent },
  { path: 'champions/:id', component: ChampionDetailComponent },
  { path: 'rotation', component: RotationComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
