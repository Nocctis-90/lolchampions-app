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
  expandedMatches: Set<string> = new Set();

  constructor(private lolService: LolService) { }

  searchMatches() {
    if (this.gameName && this.tagLine) {
      this.loading = true;
      this.errorMessage = '';
      this.playerInfo = null;
      this.matches = [];
      this.expandedMatches.clear();

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

  toggleMatch(matchId: string) {
    if (this.expandedMatches.has(matchId)) {
      this.expandedMatches.delete(matchId);
    } else {
      this.expandedMatches.add(matchId);
    }
  }

  isExpanded(matchId: string): boolean {
    return this.expandedMatches.has(matchId);
  }

  loadPlayerProfile(gameName: string, tagLine: string, event: Event) {
    // Prevenir propagação para não expandir/colapsar o match
    event.stopPropagation();

    // Atualizar campos e fazer nova busca
    this.gameName = gameName;
    this.tagLine = tagLine;
    this.searchMatches();
  }
}
