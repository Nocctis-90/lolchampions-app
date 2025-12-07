import { Component, OnInit } from '@angular/core';
import { Champion } from '../../models/lol.model';
import { LolService } from '../../services/lol.service';

@Component({
  selector: 'app-champion-list',
  templateUrl: './champion-list.component.html',
  styleUrls: ['./champion-list.component.css']
})
export class ChampionListComponent implements OnInit {
  champions: Champion[] = [];
  version = '13.24.1';

  constructor(private lolService: LolService) { }

  ngOnInit(): void {
    this.lolService.getChampions().subscribe(champions => {
      this.champions = champions;
    });
  }
}
