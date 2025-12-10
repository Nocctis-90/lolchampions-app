import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Champion, ChampionDetail, Item, Match, PlayerInfo, RankedInfo, SeasonStats } from '../models/lol.model';

@Injectable({
    providedIn: 'root'
})
export class LolService {
    private apiUrl = 'http://localhost:8084/api';

    constructor(private http: HttpClient) { }

    getChampions(): Observable<Champion[]> {
        return this.http.get<Champion[]>(`${this.apiUrl}/champions`);
    }

    getChampionDetail(id: string): Observable<ChampionDetail> {
        return this.http.get<ChampionDetail>(`${this.apiUrl}/champions/${id}`);
    }

    getItems(): Observable<Item[]> {
        return this.http.get<Item[]>(`${this.apiUrl}/items`);
    }

    getMatches(gameName: string, tag: string): Observable<Match[]> {
        return this.http.get<Match[]>(`${this.apiUrl}/matches/${gameName}/${tag}`);
    }

    getPlayerInfo(gameName: string, tag: string): Observable<PlayerInfo> {
        return this.http.get<PlayerInfo>(`${this.apiUrl}/matches/player/${gameName}/${tag}`);
    }

    getFreeRotation(): Observable<Champion[]> {
        return this.http.get<Champion[]>(`${this.apiUrl}/champions/rotation`);
    }

    getRankedInfo(puuid: string): Observable<RankedInfo[]> {
        return this.http.get<RankedInfo[]>(`${this.apiUrl}/ranked/${puuid}`);
    }

    getSeasonStats(puuid: string, season: number): Observable<SeasonStats> {
        return this.http.get<SeasonStats>(`${this.apiUrl}/season/${puuid}/${season}`);
    }
}
