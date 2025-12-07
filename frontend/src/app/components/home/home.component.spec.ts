import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormsModule } from '@angular/forms';
import { of, throwError } from 'rxjs';

import { HomeComponent } from './home.component';
import { LolService } from '../../services/lol.service';
import { Match, PlayerInfo } from '../../models/lol.model';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;
  let lolService: jasmine.SpyObj<LolService>;

  beforeEach(async () => {
    const spy = jasmine.createSpyObj('LolService', ['getMatches', 'getPlayerInfo']);

    await TestBed.configureTestingModule({
      declarations: [HomeComponent],
      imports: [HttpClientTestingModule, FormsModule],
      providers: [{ provide: LolService, useValue: spy }]
    }).compileComponents();

    lolService = TestBed.inject(LolService) as jasmine.SpyObj<LolService>;
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize with empty values', () => {
    expect(component.gameName).toBe('');
    expect(component.tagLine).toBe('');
    expect(component.matches).toEqual([]);
    expect(component.playerInfo).toBeNull();
    expect(component.errorMessage).toBe('');
    expect(component.loading).toBe(false);
  });

  describe('searchMatches', () => {
    it('should show error when gameName is empty', () => {
      component.gameName = '';
      component.tagLine = 'BR1';

      component.searchMatches();

      expect(component.errorMessage).toBe('Please enter both Game Name and Tag Line.');
    });

    it('should show error when tagLine is empty', () => {
      component.gameName = 'TestPlayer';
      component.tagLine = '';

      component.searchMatches();

      expect(component.errorMessage).toBe('Please enter both Game Name and Tag Line.');
    });

    it('should fetch player info and matches when both fields are filled', () => {
      const mockPlayer: PlayerInfo = {
        gameName: 'TestPlayer',
        tagLine: 'BR1',
        puuid: 'test-puuid',
        profileIconId: 4567,
        summonerLevel: 150,
        profileIconUrl: 'https://example.com/icon.png'
      };

      const mockMatches: Match[] = [
        { matchId: 'BR1_123', championName: 'Ahri', win: true, gameMode: 'CLASSIC', kda: '10/2/5', gameDate: '2024-01-15' }
      ];

      lolService.getPlayerInfo.and.returnValue(of(mockPlayer));
      lolService.getMatches.and.returnValue(of(mockMatches));

      component.gameName = 'TestPlayer';
      component.tagLine = 'BR1';
      component.searchMatches();

      expect(component.loading).toBe(false);
      expect(lolService.getPlayerInfo).toHaveBeenCalledWith('TestPlayer', 'BR1');
      expect(lolService.getMatches).toHaveBeenCalledWith('TestPlayer', 'BR1');
    });

    it('should handle error when fetching matches fails', () => {
      const mockPlayer: PlayerInfo = {
        gameName: 'TestPlayer',
        tagLine: 'BR1',
        puuid: 'test-puuid',
        profileIconId: 4567,
        summonerLevel: 150,
        profileIconUrl: 'https://example.com/icon.png'
      };

      lolService.getPlayerInfo.and.returnValue(of(mockPlayer));
      lolService.getMatches.and.returnValue(throwError(() => new Error('API Error')));

      component.gameName = 'TestPlayer';
      component.tagLine = 'BR1';
      component.searchMatches();

      expect(component.errorMessage).toBe('Failed to fetch matches. Please try again.');
      expect(component.loading).toBe(false);
    });

    it('should clear previous data when searching', () => {
      component.matches = [{ matchId: 'old', championName: 'Old', win: false, gameMode: 'OLD', kda: '0/0/0', gameDate: '2020-01-01' }];
      component.playerInfo = { gameName: 'Old', tagLine: 'XX', puuid: '', profileIconId: 0, summonerLevel: 0, profileIconUrl: '' };
      component.errorMessage = 'Old error';

      lolService.getPlayerInfo.and.returnValue(of({} as PlayerInfo));
      lolService.getMatches.and.returnValue(of([]));

      component.gameName = 'New';
      component.tagLine = 'BR1';
      component.searchMatches();

      expect(component.errorMessage).toBe('');
    });
  });
});
