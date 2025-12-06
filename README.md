# League of Legends Project

This is a Spring Boot application that acts as a Wiki/Database for League of Legends, fetching data from the Data Dragon API.

## Features

- **Champions**: List all champions with their splash art and tags.
- **Champion Details**: View detailed information for each champion including Lore, Passive Skills, Spells (Q, W, E, R), and Skins.
- **Items**: Browse in-game items with prices, stats, and descriptions (with tooltips).
- **Navigation**: Central menu to access different sections.

## Project Structure

This project follows the standard Maven directory layout:

- `src/main/java`: Source code
  - `com.example.lol.controller`: Web Controllers
  - `com.example.lol.model`: Data Models (Champion, Item, etc.)
  - `com.example.lol.service`: Business Logic (API Fetching)
- `src/main/resources`: Configuration and Static Assets
  - `templates`: Thymeleaf HTML templates (UI)
  - `static/css`: CSS Styles

## How to Run

1.  Ensure you have Java 17+ and Maven installed.
2.  Run the following command:
    ```bash
    mvn spring-boot:run
    ```
3.  Access the application at `http://localhost:8083` (or port defined in application.properties).
