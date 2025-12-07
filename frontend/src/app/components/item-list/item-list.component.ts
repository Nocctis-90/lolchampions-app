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
  filteredItems: Item[] = [];
  version = '15.24.1';
  loading = true;
  searchQuery = '';
  selectedCategory = 'all';
  private readonly DDRAGON_BASE = 'https://ddragon.leagueoflegends.com/cdn';

  // Item categories for filtering
  categories = [
    { key: 'all', label: 'Todos' },
    { key: 'Damage', label: 'Dano' },
    { key: 'CriticalStrike', label: 'Crítico' },
    { key: 'AttackSpeed', label: 'Vel. Ataque' },
    { key: 'LifeSteal', label: 'Roubo de Vida' },
    { key: 'SpellDamage', label: 'Poder de Habilidade' },
    { key: 'Mana', label: 'Mana' },
    { key: 'Armor', label: 'Armadura' },
    { key: 'SpellBlock', label: 'Resist. Mágica' },
    { key: 'Health', label: 'Vida' },
    { key: 'Boots', label: 'Botas' }
  ];

  constructor(private lolService: LolService) { }

  ngOnInit(): void {
    this.lolService.getItems().subscribe({
      next: (items) => {
        this.items = this.filterValidItems(items);
        this.filteredItems = this.items;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error loading items:', err);
        this.loading = false;
      }
    });
  }

  private filterValidItems(items: Item[]): Item[] {
    const seen = new Set<string>();
    return items
      .filter(item => {
        if (!item.name || !item.image || !item.image.full || !item.gold) {
          return false;
        }
        // Only show purchasable items with cost > 0
        if (!item.gold.purchasable || item.gold.total === 0) {
          return false;
        }
        // Skip special/internal items
        if (item.name.includes('Placeholder') || item.name.includes('(')) {
          return false;
        }
        if (seen.has(item.name)) {
          return false;
        }
        seen.add(item.name);
        return true;
      })
      .sort((a, b) => a.name.localeCompare(b.name));
  }

  getItemImageUrl(item: Item): string {
    if (!item.image || !item.image.full) {
      return '';
    }
    return `${this.DDRAGON_BASE}/${this.version}/img/item/${item.image.full}`;
  }

  hasValidImage(item: Item): boolean {
    return !!(item.image && item.image.full);
  }

  onImageError(event: Event): void {
    const img = event.target as HTMLImageElement;
    img.style.display = 'none';
  }

  filterItems(): void {
    let result = this.items;

    // Filter by category
    if (this.selectedCategory !== 'all') {
      result = result.filter(item =>
        item.tags && item.tags.includes(this.selectedCategory)
      );
    }

    // Filter by search query
    if (this.searchQuery.trim()) {
      const query = this.searchQuery.toLowerCase();
      result = result.filter(item =>
        item.name.toLowerCase().includes(query) ||
        (item.description && item.description.toLowerCase().includes(query))
      );
    }

    this.filteredItems = result;
  }

  selectCategory(category: string): void {
    this.selectedCategory = category;
    this.filterItems();
  }

  formatGold(gold: number): string {
    return gold.toLocaleString('pt-BR');
  }

  /**
   * Clean HTML description and extract stats
   */
  getCleanDescription(html: string): string {
    if (!html) return '';
    // Remove HTML tags but keep the text
    let text = html
      .replace(/<br\s*\/?>/gi, '\n')
      .replace(/<[^>]*>/g, '')
      .replace(/&nbsp;/g, ' ')
      .trim();

    // Limit length
    if (text.length > 150) {
      text = text.substring(0, 150) + '...';
    }
    return text;
  }

  /**
   * Extract stats from description
   */
  extractStats(html: string): string[] {
    if (!html) return [];
    const stats: string[] = [];

    // Common stat patterns
    const patterns = [
      /\+(\d+)\s*de\s*Poder\s*de\s*Habilidade/gi,
      /\+(\d+)\s*de\s*Dano\s*de\s*Ataque/gi,
      /\+(\d+)\s*de\s*Armadura/gi,
      /\+(\d+)\s*de\s*Vida/gi,
      /\+(\d+)\s*de\s*Mana/gi,
      /\+(\d+)%\s*de\s*Velocidade\s*de\s*Ataque/gi,
      /\+(\d+)%\s*de\s*Chance\s*de\s*Acerto\s*Cr[ií]tico/gi,
      /\+(\d+)\s*de\s*Resist[êe]ncia\s*M[áa]gica/gi,
    ];

    // Extract first 3-4 stats for display
    const text = html.replace(/<[^>]*>/g, '');
    const statMatches = text.match(/\+\d+[%]?\s*de\s*[^+<]*/gi);
    if (statMatches) {
      return statMatches.slice(0, 4).map(s => s.trim());
    }

    return stats;
  }

  trackByName(index: number, item: Item): string {
    return item.name;
  }
}
