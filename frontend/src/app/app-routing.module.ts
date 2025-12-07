import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ItemListComponent } from './components/item-list/item-list.component';

import { ChampionListComponent } from './components/champion-list/champion-list.component';
import { ChampionDetailComponent } from './components/champion-detail/champion-detail.component';

const routes: Routes = [
  { path: 'items', component: ItemListComponent },
  { path: 'champions', component: ChampionListComponent },
  { path: 'champions/:id', component: ChampionDetailComponent },
  { path: '', redirectTo: '/items', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
