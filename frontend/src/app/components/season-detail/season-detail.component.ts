import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LolService } from '../../services/lol.service';
import { SeasonStats, ChampionSeasonStats } from '../../models/lol.model';

@Component({
    selector: 'app-season-detail',
    templateUrl: './season-detail.component.html',
    styleUrls: ['./season-detail.component.css']
})
export class SeasonDetailComponent implements OnInit {
    puuid: string = '';
    season: number = 0;
    seasonStats: SeasonStats | null = null;
    loading: boolean = true;
    error: string = '';

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private lolService: LolService
    ) { }

    ngOnInit() {
        this.route.params.subscribe(params => {
            this.puuid = params['puuid'];
            this.season = +params['season'];
            this.loadSeasonStats();
        });
    }

    loadSeasonStats() {
        this.loading = true;
        this.error = '';

        this.lolService.getSeasonStats(this.puuid, this.season).subscribe({
            next: (stats) => {
                this.seasonStats = stats;
                this.loading = false;
            },
            error: (err) => {
                console.error('Error loading season stats:', err);
                this.error = 'Failed to load season statistics';
                this.loading = false;
            }
        });
    }

    goBack() {
        this.router.navigate(['/']);
    }

    getRankEmblemUrl(): string {
        if (!this.seasonStats?.tier || this.seasonStats.tier === 'UNRANKED') {
            return 'https://raw.communitydragon.org/latest/plugins/rcp-fe-lol-static-assets/global/default/images/ranked-emblem/emblem-iron.png';
        }
        const tier = this.seasonStats.tier.toLowerCase();
        return `https://raw.communitydragon.org/latest/plugins/rcp-fe-lol-static-assets/global/default/images/ranked-emblem/emblem-${tier}.png`;
    }

    getFullRank(): string {
        if (!this.seasonStats?.tier || this.seasonStats.tier === 'UNRANKED') return 'Unranked';
        const tier = this.formatTier(this.seasonStats.tier);
        if (this.seasonStats.tier === 'MASTER' || this.seasonStats.tier === 'GRANDMASTER' || this.seasonStats.tier === 'CHALLENGER') {
            return tier;
        }
        return `${tier} ${this.seasonStats.division}`;
    }

    formatTier(tier: string): string {
        if (!tier) return '';
        return tier.charAt(0) + tier.slice(1).toLowerCase();
    }

    getWinRateClass(winRate: number): string {
        if (winRate >= 60) return 'winrate-high';
        if (winRate >= 50) return 'winrate-mid';
        return 'winrate-low';
    }

    formatNumber(num: number): string {
        return num.toFixed(1);
    }

    formatWinRate(winRate: number): string {
        return winRate.toFixed(1) + '%';
    }
}
