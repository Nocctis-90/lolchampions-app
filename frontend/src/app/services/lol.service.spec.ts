import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { LolService } from './lol.service';
import { Champion, ChampionDetail, Item, Match, PlayerInfo } from '../models/lol.model';

describe('LolService', () => {
    let service: LolService;
    let httpMock: HttpTestingController;
    const apiUrl = 'http://localhost:8084/api';

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [LolService]
        });
        service = TestBed.inject(LolService);
        httpMock = TestBed.inject(HttpTestingController);
    });

    afterEach(() => {
        httpMock.verify();
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });

    describe('getChampions', () => {
        it('should return list of champions', () => {
            const mockChampions: Champion[] = [
                { id: 'Ahri', key: '103', name: 'Ahri', title: 'the Nine-Tailed Fox', blurb: '', image: {} as any, tags: ['Mage', 'Assassin'] }
            ];

            service.getChampions().subscribe(champions => {
                expect(champions.length).toBe(1);
                expect(champions[0].name).toBe('Ahri');
            });

            const req = httpMock.expectOne(`${apiUrl}/champions`);
            expect(req.request.method).toBe('GET');
            req.flush(mockChampions);
        });
    });

    describe('getChampionDetail', () => {
        it('should return champion detail', () => {
            const mockDetail: ChampionDetail = {
                id: 'Ahri',
                key: '103',
                name: 'Ahri',
                title: 'the Nine-Tailed Fox',
                blurb: '',
                image: {} as any,
                tags: [],
                lore: 'Test lore',
                passive: {} as any,
                spells: [],
                skins: []
            };

            service.getChampionDetail('Ahri').subscribe(detail => {
                expect(detail.id).toBe('Ahri');
                expect(detail.lore).toBe('Test lore');
            });

            const req = httpMock.expectOne(`${apiUrl}/champions/Ahri`);
            expect(req.request.method).toBe('GET');
            req.flush(mockDetail);
        });
    });

    describe('getItems', () => {
        it('should return list of items', () => {
            const mockItems: Item[] = [
                { name: "Doran's Blade", description: 'Test', plaintext: '', image: {} as any, gold: {} as any, tags: [] }
            ];

            service.getItems().subscribe(items => {
                expect(items.length).toBe(1);
                expect(items[0].name).toBe("Doran's Blade");
            });

            const req = httpMock.expectOne(`${apiUrl}/items`);
            expect(req.request.method).toBe('GET');
            req.flush(mockItems);
        });
    });

    describe('getMatches', () => {
        it('should return list of matches for player', () => {
            const mockMatches: Match[] = [
                { matchId: 'BR1_123', championName: 'Ahri', win: true, gameMode: 'CLASSIC', kda: '10/2/5', gameDate: '2024-01-15' }
            ];

            service.getMatches('TestPlayer', 'BR1').subscribe(matches => {
                expect(matches.length).toBe(1);
                expect(matches[0].championName).toBe('Ahri');
                expect(matches[0].win).toBe(true);
            });

            const req = httpMock.expectOne(`${apiUrl}/matches/TestPlayer/BR1`);
            expect(req.request.method).toBe('GET');
            req.flush(mockMatches);
        });
    });

    describe('getPlayerInfo', () => {
        it('should return player info', () => {
            const mockPlayer: PlayerInfo = {
                gameName: 'TestPlayer',
                tagLine: 'BR1',
                puuid: 'test-puuid',
                profileIconId: 4567,
                summonerLevel: 150,
                profileIconUrl: 'https://example.com/icon.png'
            };

            service.getPlayerInfo('TestPlayer', 'BR1').subscribe(player => {
                expect(player.gameName).toBe('TestPlayer');
                expect(player.summonerLevel).toBe(150);
            });

            const req = httpMock.expectOne(`${apiUrl}/matches/player/TestPlayer/BR1`);
            expect(req.request.method).toBe('GET');
            req.flush(mockPlayer);
        });
    });

    describe('getFreeRotation', () => {
        it('should return list of free rotation champions', () => {
            const mockChampions: Champion[] = [
                { id: 'Ahri', key: '103', name: 'Ahri', title: 'the Nine-Tailed Fox', blurb: '', image: {} as any, tags: [] }
            ];

            service.getFreeRotation().subscribe(champions => {
                expect(champions.length).toBe(1);
                expect(champions[0].name).toBe('Ahri');
            });

            const req = httpMock.expectOne(`${apiUrl}/champions/rotation`);
            expect(req.request.method).toBe('GET');
            req.flush(mockChampions);
        });
    });
});
