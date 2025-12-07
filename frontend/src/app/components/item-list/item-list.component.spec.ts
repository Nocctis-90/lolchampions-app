import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of, throwError } from 'rxjs';

import { ItemListComponent } from './item-list.component';
import { LolService } from '../../services/lol.service';
import { Item } from '../../models/lol.model';

describe('ItemListComponent', () => {
  let component: ItemListComponent;
  let fixture: ComponentFixture<ItemListComponent>;
  let lolService: jasmine.SpyObj<LolService>;

  beforeEach(async () => {
    const spy = jasmine.createSpyObj('LolService', ['getItems']);

    await TestBed.configureTestingModule({
      declarations: [ItemListComponent],
      imports: [HttpClientTestingModule],
      providers: [{ provide: LolService, useValue: spy }]
    }).compileComponents();

    lolService = TestBed.inject(LolService) as jasmine.SpyObj<LolService>;
  });

  beforeEach(() => {
    lolService.getItems.and.returnValue(of([]));
    fixture = TestBed.createComponent(ItemListComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch items on init', () => {
    const mockItems: Item[] = [
      { name: "Doran's Blade", description: 'Test', plaintext: '', image: { full: 'item.png' } as any, gold: { base: 450, total: 450, sell: 180, purchasable: true }, tags: ['Damage'] },
      { name: 'Long Sword', description: 'Test 2', plaintext: '', image: { full: 'item2.png' } as any, gold: { base: 350, total: 350, sell: 140, purchasable: true }, tags: ['Damage'] }
    ];

    lolService.getItems.and.returnValue(of(mockItems));

    component.ngOnInit();

    expect(lolService.getItems).toHaveBeenCalled();
    expect(component.items.length).toBe(2);
    expect(component.items[0].name).toBe("Doran's Blade");
  });

  it('should handle error when fetching items fails', () => {
    spyOn(console, 'error');
    lolService.getItems.and.returnValue(throwError(() => new Error('API Error')));

    component.ngOnInit();

    expect(console.error).toHaveBeenCalled();
  });

  it('should have empty items array initially', () => {
    expect(component.items).toEqual([]);
  });
});
