import { Component } from '@angular/core';
import { LolService } from '../../services/lol.service';
import { Match, PlayerInfo, RankedInfo } from '../../models/lol.model';

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
  rankedInfo: RankedInfo | null = null;
  errorMessage: string = '';
  loading: boolean = false;
  expandedMatches: Set<string> = new Set();

  constructor(private lolService: LolService) { }

  searchMatches() {
    if (this.gameName && this.tagLine) {
      this.loading = true;
      this.errorMessage = '';
      this.playerInfo = null;
      this.rankedInfo = null;
      this.matches = [];
      this.expandedMatches.clear();

      // First get player info
      this.lolService.getPlayerInfo(this.gameName, this.tagLine).subscribe({
        next: (data) => {
          this.playerInfo = data;

          // After getting player info, fetch ranked data
          if (data.puuid) {
            this.fetchRankedData(data.puuid);
          }
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

  fetchRankedData(puuid: string) {
    // Fetch current ranked info only (real data from Riot API)
    this.lolService.getRankedInfo(puuid).subscribe({
      next: (rankedData) => {
        console.log('Ranked data received:', rankedData);
        // Find Solo/Duo queue info
        this.rankedInfo = rankedData.find(r => r.queueType === 'RANKED_SOLO_5x5') || null;
      },
      error: (err) => {
        console.error('Error fetching ranked info:', err);
      }
    });
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
    event.stopPropagation();
    this.gameName = gameName;
    this.tagLine = tagLine;
    this.searchMatches();
  }

  // Utility methods for ranked display
  getFullRank(): string {
    if (!this.rankedInfo) return 'Unranked';
    const tier = this.rankedInfo.tier;
    if (tier === 'MASTER' || tier === 'GRANDMASTER' || tier === 'CHALLENGER') {
      return this.formatTier(tier);
    }
    return `${this.formatTier(tier)} ${this.rankedInfo.rank}`;
  }

  formatTier(tier: string): string {
    if (!tier) return '';
    return tier.charAt(0) + tier.slice(1).toLowerCase();
  }

  getTotalGames(): number {
    if (!this.rankedInfo) return 0;
    return this.rankedInfo.wins + this.rankedInfo.losses;
  }

  getWinRate(): string {
    const total = this.getTotalGames();
    if (total === 0) return '0%';
    return Math.round((this.rankedInfo!.wins * 100) / total) + '%';
  }

  // Ranked emblem image URL from CommunityDragon
  getRankEmblemUrl(): string {
    if (!this.rankedInfo?.tier) {
      return 'https://raw.communitydragon.org/latest/plugins/rcp-fe-lol-static-assets/global/default/images/ranked-emblem/emblem-iron.png';
    }
    const tier = this.rankedInfo.tier.toLowerCase();
    return `https://raw.communitydragon.org/latest/plugins/rcp-fe-lol-static-assets/global/default/images/ranked-emblem/emblem-${tier}.png`;
  }
}
