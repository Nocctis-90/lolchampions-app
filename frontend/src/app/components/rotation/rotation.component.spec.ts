import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { of, throwError } from 'rxjs';

import { RotationComponent } from './rotation.component';
import { LolService } from '../../services/lol.service';
import { Champion } from '../../models/lol.model';

describe('RotationComponent', () => {
    let component: RotationComponent;
    let fixture: ComponentFixture<RotationComponent>;
    let lolService: jasmine.SpyObj<LolService>;

    beforeEach(async () => {
        const spy = jasmine.createSpyObj('LolService', ['getFreeRotation']);

        await TestBed.configureTestingModule({
            declarations: [RotationComponent],
            imports: [HttpClientTestingModule, RouterTestingModule],
            providers: [{ provide: LolService, useValue: spy }]
        }).compileComponents();

        lolService = TestBed.inject(LolService) as jasmine.SpyObj<LolService>;
    });

    beforeEach(() => {
        lolService.getFreeRotation.and.returnValue(of([]));
        fixture = TestBed.createComponent(RotationComponent);
        component = fixture.componentInstance;
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should initialize with loading true', () => {
        expect(component.loading).toBe(true);
        expect(component.freeChampions).toEqual([]);
    });

    it('should fetch free rotation on init', () => {
        const mockChampions: Champion[] = [
            { id: 'Ahri', key: '103', name: 'Ahri', title: 'the Nine-Tailed Fox', blurb: '', image: { full: 'Ahri.png' } as any, tags: ['Mage'] }
        ];

        lolService.getFreeRotation.and.returnValue(of(mockChampions));

        component.ngOnInit();

        expect(lolService.getFreeRotation).toHaveBeenCalled();
        expect(component.freeChampions).toEqual(mockChampions);
        expect(component.loading).toBe(false);
    });

    it('should set loading to false on error', () => {
        lolService.getFreeRotation.and.returnValue(throwError(() => new Error('API Error')));

        component.ngOnInit();

        expect(component.loading).toBe(false);
        expect(component.freeChampions).toEqual([]);
    });

    it('should generate correct champion image URL', () => {
        const champion: Champion = {
            id: 'Ahri',
            key: '103',
            name: 'Ahri',
            title: 'the Nine-Tailed Fox',
            blurb: '',
            image: { full: 'Ahri.png' } as any,
            tags: []
        };

        const url = component.getChampionImageUrl(champion);

        expect(url).toContain('Ahri.png');
        expect(url).toContain('ddragon.leagueoflegends.com');
    });
});
