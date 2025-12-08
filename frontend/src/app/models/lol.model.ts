export interface Image {
    full: string;
    sprite: string;
    group: string;
    x: number;
    y: number;
    w: number;
    h: number;
}

export interface Gold {
    base: number;
    purchasable: boolean;
    total: number;
    sell: number;
}

export interface Item {
    name: string;
    description: string;
    plaintext: string;
    image: Image;
    gold: Gold;
    tags: string[];
}

export interface Skin {
    id: string;
    num: number;
    name: string;
    chromas: boolean;
}

export interface Spell {
    id: string;
    name: string;
    description: string;
    image: Image;
}

export interface Passive {
    name: string;
    description: string;
    image: Image;
}

export interface Champion {
    id: string;
    key: string;
    name: string;
    title: string;
    blurb: string;
    image: Image;
    tags: string[];
}

export interface ChampionDetail extends Champion {
    lore: string;
    passive: Passive;
    spells: Spell[];
    skins: Skin[];
}

export interface MatchParticipantSummary {
    summonerName: string;
    tagline: string;
    championName: string;
    championImageUrl: string;
    kda: string;
    win: boolean;
    currentPlayer: boolean;
    itemImageUrls: string[];
    farm: number;
}

export interface Match {
    matchId: string;
    championName: string;
    championImageUrl: string;
    win: boolean;
    remake: boolean;
    gameMode: string;
    queueType: string;
    kda: string;
    gameDate: string;
    gameDurationMinutes: number;
    gameDurationSeconds: number;
    team1: MatchParticipantSummary[];
    team2: MatchParticipantSummary[];
}

export interface PlayerInfo {
    gameName: string;
    tagLine: string;
    puuid: string;
    profileIconId: number;
    summonerLevel: number;
    profileIconUrl: string;
}

