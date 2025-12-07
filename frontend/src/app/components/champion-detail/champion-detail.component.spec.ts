import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { ActivatedRoute } from '@angular/router';
import { of, throwError } from 'rxjs';

import { ChampionDetailComponent } from './champion-detail.component';
import { LolService } from '../../services/lol.service';
import { ChampionDetail } from '../../models/lol.model';

describe('ChampionDetailComponent', () => {
  let component: ChampionDetailComponent;
  let fixture: ComponentFixture<ChampionDetailComponent>;
  let lolService: jasmine.SpyObj<LolService>;

  beforeEach(async () => {
    const spy = jasmine.createSpyObj('LolService', ['getChampionDetail']);

    await TestBed.configureTestingModule({
      declarations: [ChampionDetailComponent],
      imports: [HttpClientTestingModule, RouterTestingModule],
      providers: [
        { provide: LolService, useValue: spy },
        {
          provide: ActivatedRoute,
          useValue: {
            params: of({ id: 'Ahri' })
          }
        }
      ]
    }).compileComponents();

    lolService = TestBed.inject(LolService) as jasmine.SpyObj<LolService>;
  });

  beforeEach(() => {
    const mockDetail: ChampionDetail = {
      id: 'Ahri',
      key: '103',
      name: 'Ahri',
      title: 'the Nine-Tailed Fox',
      blurb: '',
      image: { full: 'Ahri.png' } as any,
      tags: ['Mage', 'Assassin'],
      lore: 'Test lore',
      passive: { name: 'Essence Theft', description: 'Test', image: {} as any },
      spells: [],
      skins: []
    };
    lolService.getChampionDetail.and.returnValue(of(mockDetail));
    fixture = TestBed.createComponent(ChampionDetailComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch champion detail on init', () => {
    const mockDetail: ChampionDetail = {
      id: 'Ahri',
      key: '103',
      name: 'Ahri',
      title: 'the Nine-Tailed Fox',
      blurb: '',
      image: { full: 'Ahri.png' } as any,
      tags: [],
      lore: 'Test lore',
      passive: {} as any,
      spells: [],
      skins: []
    };

    lolService.getChampionDetail.and.returnValue(of(mockDetail));

    component.ngOnInit();

    expect(lolService.getChampionDetail).toHaveBeenCalledWith('Ahri');
    expect(component.champion?.name).toBe('Ahri');
  });

  it('should handle null champion detail', () => {
    lolService.getChampionDetail.and.returnValue(of(null as any));

    component.ngOnInit();

    expect(component.champion).toBeNull();
  });

  it('should handle error when fetching champion detail', () => {
    spyOn(console, 'error');
    lolService.getChampionDetail.and.returnValue(throwError(() => new Error('API Error')));

    component.ngOnInit();

    expect(console.error).toHaveBeenCalled();
  });
});
