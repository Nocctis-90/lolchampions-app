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
    summonerId?: string;  // Added to fetch ranked data
    profileIconId: number;
    summonerLevel: number;
    profileIconUrl: string;
}

export interface RankedInfo {
    queueType: string;    // RANKED_SOLO_5x5, RANKED_FLEX_SR
    tier: string;         // IRON, BRONZE, SILVER, GOLD, etc.
    rank: string;         // I, II, III, IV
    leaguePoints: number;
    wins: number;
    losses: number;
    hotStreak: boolean;
    veteran: boolean;
    freshBlood: boolean;
    inactive: boolean;
}

export interface SeasonRank {
    season: number;
    tier: string;
    division: string;
    queueType: string;
}

export interface ChampionSeasonStats {
    championId: string;
    championName: string;
    championImageUrl: string;
    gamesPlayed: number;
    wins: number;
    losses: number;
    winRate: number;
    avgKills: number;
    avgDeaths: number;
    avgAssists: number;
    avgCs: number;
    doubleKills: number;
    tripleKills: number;
    quadraKills: number;
    pentaKills: number;
}

export interface SeasonStats {
    season: number;
    tier: string;
    division: string;
    puuid: string;
    totalGames: number;
    totalWins: number;
    totalLosses: number;
    overallWinRate: number;
    totalDoubleKills: number;
    totalTripleKills: number;
    totalQuadraKills: number;
    totalPentaKills: number;
    avgCs: number;
    championStats: ChampionSeasonStats[];
}
