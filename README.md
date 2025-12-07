# League of Legends Project

This project is a modern web application that acts as a Wiki/Explorer for League of Legends, fetching real-time data from the Data Dragon API.

It has been migrated to a split **Client-Server Architecture**:
- **Backend**: Spring Boot (Java) REST API.
- **Frontend**: Angular Single Page Application (SPA).

## Features

- **Champions**: Browse all champions with high-quality splash art.
- **Champion Details**: deeply detail views including Lore, Passives, Spells, and Skins.
- **Items**: Complete item database with prices, tooltips, and stats.
- **Modern UI**: Dark-themed, responsive design tailored for gamers.

## Tech Stack

- **Backend**: Java 17, Spring Boot 3.2, Maven.
- **Frontend**: Angular 12+, TypeScript, CSS3.

## Project Structure

- **Backend** (`/src`):
  - `com.example.lol.controller`: REST Controllers (API endpoints).
  - `com.example.lol.service`: Logic to fetch data from Riot's Data Dragon.
- **Frontend** (`/frontend`):
  - Angular application source code.
  - Components for Champions, Items, and Details.

## How to Run

### Prerequisites
- Java 17+
- Maven
- Node.js (v14+) & npm
- Angular CLI (`npm install -g @angular/cli`)

### 1. Start the Backend (API)
The backend runs on port `8084` and serves the data.

```bash
# In the root directory
mvn spring-boot:run
```

### 2. Start the Frontend (UI)
The frontend runs on port `4200`.

```bash
# Open a new terminal
cd frontend
npm install # Only mainly for the first time
ng serve
```

### 3. Access the App
Open your browser and navigate to:
**[http://localhost:4200](http://localhost:4200)**
