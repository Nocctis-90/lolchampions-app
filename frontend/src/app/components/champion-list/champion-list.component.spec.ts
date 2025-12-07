import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { of, throwError } from 'rxjs';

import { ChampionListComponent } from './champion-list.component';
import { LolService } from '../../services/lol.service';
import { Champion } from '../../models/lol.model';

describe('ChampionListComponent', () => {
  let component: ChampionListComponent;
  let fixture: ComponentFixture<ChampionListComponent>;
  let lolService: jasmine.SpyObj<LolService>;

  beforeEach(async () => {
    const spy = jasmine.createSpyObj('LolService', ['getChampions']);

    await TestBed.configureTestingModule({
      declarations: [ChampionListComponent],
      imports: [HttpClientTestingModule, RouterTestingModule],
      providers: [{ provide: LolService, useValue: spy }]
    }).compileComponents();

    lolService = TestBed.inject(LolService) as jasmine.SpyObj<LolService>;
  });

  beforeEach(() => {
    lolService.getChampions.and.returnValue(of([]));
    fixture = TestBed.createComponent(ChampionListComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch champions on init', () => {
    const mockChampions: Champion[] = [
      { id: 'Ahri', key: '103', name: 'Ahri', title: 'the Nine-Tailed Fox', blurb: '', image: { full: 'Ahri.png' } as any, tags: ['Mage'] },
      { id: 'Zed', key: '238', name: 'Zed', title: 'the Master of Shadows', blurb: '', image: { full: 'Zed.png' } as any, tags: ['Assassin'] }
    ];

    lolService.getChampions.and.returnValue(of(mockChampions));

    component.ngOnInit();

    expect(lolService.getChampions).toHaveBeenCalled();
    expect(component.champions.length).toBe(2);
  });

  it('should handle error when fetching champions fails', () => {
    spyOn(console, 'error');
    lolService.getChampions.and.returnValue(throwError(() => new Error('API Error')));

    component.ngOnInit();

    expect(console.error).toHaveBeenCalled();
  });

  it('should have empty champions array initially', () => {
    expect(component.champions).toEqual([]);
  });
});
