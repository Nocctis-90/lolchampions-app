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
  filteredChampions: Champion[] = [];
  version = '15.24.1';
  loading = true;
  searchQuery = '';
  private readonly DDRAGON_BASE = 'https://ddragon.leagueoflegends.com/cdn';
  private readonly PLACEHOLDER_IMAGE = 'assets/placeholder-champion.png';

  constructor(private lolService: LolService) { }

  ngOnInit(): void {
    this.lolService.getChampions().subscribe({
      next: (champions) => {
        // Remove duplicates by id
        const uniqueChampions = this.removeDuplicates(champions);
        // Sort alphabetically
        this.champions = uniqueChampions.sort((a, b) => a.name.localeCompare(b.name));
        this.filteredChampions = this.champions;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error loading champions:', err);
        this.loading = false;
      }
    });
  }

  /**
   * Removes duplicate champions by id
   */
  private removeDuplicates(champions: Champion[]): Champion[] {
    const seen = new Set<string>();
    return champions.filter(champion => {
      if (seen.has(champion.id)) {
        return false;
      }
      seen.add(champion.id);
      return true;
    });
  }

  /**
   * Builds the champion image URL with fallback
   */
  getChampionImageUrl(champion: Champion): string {
    if (!champion.image || !champion.image.full) {
      return this.PLACEHOLDER_IMAGE;
    }
    return `${this.DDRAGON_BASE}/${this.version}/img/champion/${champion.image.full}`;
  }

  /**
   * Checks if champion has a valid image
   */
  hasValidImage(champion: Champion): boolean {
    return !!(champion.image && champion.image.full);
  }

  /**
   * Handles image loading errors
   */
  onImageError(event: Event): void {
    const img = event.target as HTMLImageElement;
    img.src = this.PLACEHOLDER_IMAGE;
    img.style.opacity = '0.5';
  }

  /**
   * Filters champions by search query
   */
  filterChampions(): void {
    if (!this.searchQuery.trim()) {
      this.filteredChampions = this.champions;
      return;
    }

    const query = this.searchQuery.toLowerCase();
    this.filteredChampions = this.champions.filter(champion =>
      champion.name.toLowerCase().includes(query) ||
      champion.title.toLowerCase().includes(query) ||
      (champion.tags && champion.tags.some(tag => tag.toLowerCase().includes(query)))
    );
  }
}
