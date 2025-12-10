# Feature: Exibição de Elo e Histórico de Seasons

## 📋 Descrição

Esta feature adiciona informações de **Ranked** (Elo) do jogador na página inicial, incluindo:
- **Elo atual** (Solo/Duo)
- **PDL** (Pontos de Liga)
- **Vitórias e Derrotas**
- **Winrate** (Taxa de vitórias)
- **Total de partidas** da season atual
- **Histórico das últimas 7 seasons**

## 🎨 Design

A seção de ranked aparece ao **lado esquerdo** do card do invocador com:
- Fundo em **tons de amarelo/dourado** (#2c2416 a #3d3120)
- Bordas douradas (#c9aa71) que harmonizam com o tema azul existente
- Layout responsivo (em mobile, aparece abaixo do card do player)

## 🔧 Implementação

### Backend (Spring Boot)

#### Novos Models:
1. **`RankedInfo.java`** - Informações de ranked atual
   - Tier, Rank, League Points
   - Wins, Losses, Win Rate
   - Hot Streak, Veteran, Fresh Blood flags

2. **`SeasonRank.java`** - Histórico de seasons
   - Season, Tier, Division

#### Novos Serviços:
1. **`RankedService.java`** / **`RankedServiceImpl.java`**
   - `getRankedInfo(summonerId)` - Busca dados de ranked da API da Riot
   - `getSeasonHistory(summonerId, seasons)` - Gera histórico mock de seasons

#### Novo Controller:
1. **`RankedController.java`**
   - `GET /api/ranked/{summonerId}` - Retorna ranked info
   - `GET /api/ranked/history/{summonerId}?seasons=7` - Retorna histórico

#### Atualizações:
- **`PlayerInfo.java`** - Adicionado campo `summonerId` necessário para buscar dados de ranked
- **`PlayerServiceImpl.java`** - Atualizado para incluir o summonerId ao criar PlayerInfo

### Frontend (Angular)

#### Novos Interfaces (TypeScript):
- **`RankedInfo`** - Dados de ranked atual
- **`SeasonRank`** - Dados de season histórica

#### Atualizações:
1. **`lol.service.ts`**
   - `getRankedInfo(summonerId)` - Método para buscar ranked info
   - `getSeasonHistory(summonerId, seasons)` - Método para buscar histórico

2. **`home.component.ts`**
   - Adicionadas propriedades: `rankedInfo`, `seasonHistory`
   - Método `fetchRankedData()` - Busca dados de ranked após obter player info
   - Métodos utilitários:
     - `getFullRank()` - Formata tier + rank
     - `formatTier()` - Capitaliza tier corretamente
     - `getTotalGames()` - Calcula total de partidas
     - `getWinRate()` - Calcula winrate
     - `getSeasonRank()` - Formata rank de season

3. **`home.component.html`**
   - Nova estrutura: `player-info-container` com grid layout
   - Seção `ranked-section` à esquerda
   - `player-card` à direita

4. **`home.component.css`**
   - Estilos para ranked section com background dourado
   - Grid layout responsivo
   - Animações e hover effects

## 🌐 Endpoints da API Riot Utilizados

1. **League-V4**: `GET /lol/league/v4/entries/by-summoner/{encryptedSummonerId}`
   - Retorna informações de ranked do jogador

## ⚠️ Nota Importante

O **histórico de seasons** utiliza dados **mock/simulados** porque a API da Riot não fornece facilmente acesso ao histórico de ranks de seasons anteriores. Para implementar isso com dados reais, seria necessário:
- Armazenar os dados em um banco de dados próprio
- Capturar o rank ao final de cada season
- Ou utilizar APIs de terceiros que mantêm esse histórico

## 🎯 Como Testar

1. Acesse: `http://localhost:4200/`
2. Pesquise por um jogador usando: **GameName#Tag**
3. Veja as informações de ranked aparecerem ao lado esquerdo do card do player
4. Observe:
   - Rank atual (ex: Gold II)
   - PDL (ex: 47 LP)
   - Estatísticas (ex: 120W 115L - 51%)
   - Histórico de 7 seasons com ranks simulados

## 📱 Responsividade

Em dispositivos móveis (< 768px):
- O layout muda para coluna única
- Player card aparece primeiro
- Ranked section aparece logo abaixo
