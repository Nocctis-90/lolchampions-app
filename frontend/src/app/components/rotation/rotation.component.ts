import { Component, OnInit } from '@angular/core';
import { LolService } from '../../services/lol.service';
import { Champion } from '../../models/lol.model';

@Component({
  selector: 'app-rotation',
  templateUrl: './rotation.component.html',
  styleUrls: ['./rotation.component.css']
})
export class RotationComponent implements OnInit {
  freeChampions: Champion[] = [];
  loading: boolean = true;
  version: string = '15.24.1';
  private readonly DDRAGON_BASE = 'https://ddragon.leagueoflegends.com/cdn';

  constructor(private lolService: LolService) { }

  ngOnInit(): void {
    this.lolService.getFreeRotation().subscribe({
      next: (data) => {
        // Remove duplicates and sort
        this.freeChampions = this.removeDuplicates(data).sort((a, b) => a.name.localeCompare(b.name));
        this.loading = false;
      },
      error: (err) => {
        console.error('Error fetching free rotation:', err);
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
   * Builds the champion image URL
   */
  getChampionImageUrl(champion: Champion): string {
    if (!champion.image || !champion.image.full) {
      return '';
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
    img.style.display = 'none';
  }
}
