import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Champion, ChampionDetail, Item } from '../models/lol.model';

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
}
