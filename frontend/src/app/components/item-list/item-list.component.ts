import { Component, OnInit } from '@angular/core';
import { Item } from '../../models/lol.model';
import { LolService } from '../../services/lol.service';

@Component({
  selector: 'app-item-list',
  templateUrl: './item-list.component.html',
  styleUrls: ['./item-list.component.css']
})
export class ItemListComponent implements OnInit {
  items: Item[] = [];
  version = '13.24.1'; // TODO: Implement version fetching

  constructor(private lolService: LolService) { }

  ngOnInit(): void {
    this.lolService.getItems().subscribe(items => {
      this.items = items;
    });
  }
}
