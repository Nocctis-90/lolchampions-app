import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ChampionDetail } from '../../models/lol.model';
import { LolService } from '../../services/lol.service';

@Component({
  selector: 'app-champion-detail',
  templateUrl: './champion-detail.component.html',
  styleUrls: ['./champion-detail.component.css']
})
export class ChampionDetailComponent implements OnInit {
  champion: ChampionDetail | undefined;
  version = '13.24.1';

  constructor(
    private route: ActivatedRoute,
    private lolService: LolService
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.lolService.getChampionDetail(id).subscribe(champion => {
        this.champion = champion;
      });
    }
  }
}
