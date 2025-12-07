import { Component } from '@angular/core';
import { LolService } from '../../services/lol.service';
import { Match, PlayerInfo } from '../../models/lol.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  gameName: string = '';
  tagLine: string = '';
  matches: Match[] = [];
  playerInfo: PlayerInfo | null = null;
  errorMessage: string = '';
  loading: boolean = false;

  constructor(private lolService: LolService) { }

  searchMatches() {
    if (this.gameName && this.tagLine) {
      this.loading = true;
      this.errorMessage = '';
      this.playerInfo = null;
      this.matches = [];

      // First get player info
      this.lolService.getPlayerInfo(this.gameName, this.tagLine).subscribe({
        next: (data) => {
          this.playerInfo = data;
        },
        error: (err) => {
          console.error('Error fetching player info:', err);
        }
      });

      // Then get matches
      this.lolService.getMatches(this.gameName, this.tagLine).subscribe({
        next: (data) => {
          this.matches = data;
          this.loading = false;
        },
        error: (err) => {
          console.error(err);
          this.errorMessage = 'Failed to fetch matches. Please try again.';
          this.loading = false;
        }
      });
    } else {
      this.errorMessage = 'Please enter both Game Name and Tag Line.';
    }
  }
}
