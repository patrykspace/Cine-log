# 🎬 CineLog — Premium Movie Journal

[![Android](https://img.shields.io/badge/Platform-Android-3DDC84?logo=android&logoColor=white)](#)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-7F52FF?logo=kotlin&logoColor=white)](#)
[![Compose](https://img.shields.io/badge/UI-Jetpack_Compose-4285F4?logo=jetpackcompose&logoColor=white)](#)
[![SQLite](https://img.shields.io/badge/Database-Room-003B57?logo=sqlite&logoColor=white)](#)

CineLog is a professional, native Android application designed for high-end movie tracking. Built with a **Dark & Gold** premium aesthetic, it combines modern reactive programming with a sleek user experience.

---

## 🏗️ System Architecture

CineLog follows the **Clean Architecture** principles using the **MVVM (Model-View-ViewModel)** pattern with **two dedicated ViewModels** for strict separation of concerns, high testability, and a robust data flow.

```mermaid
graph TD
    subgraph UI_Layer [UI Layer - Jetpack Compose]
        A[MainActivity] --> B[Navigation Graph]
        B --> C[DashboardScreen]
        B --> D[MoviesScreen]
        B --> E[ProfileScreen]
        B --> F[MovieDetailScreen]
        B --> G2[LibraryScreen]
        B --> G3[AddMovieScreen]
    end

    subgraph Logic_Layer [State Management — 2 ViewModels]
        G["MovieListViewModel\n(collections, insert, delete, seed)"]
        G1["MovieDetailViewModel\n(favorite, watched, review, update, allMovies)"]
    end

    subgraph Data_Layer [Data Persistence]
        H[MovieRepository]
        I[Room Database]
        J[MovieDao]
    end

    C & D & E & G2 & G3 <--> G
    F <--> G1
    G & G1 <--> H
    H <--> J
    J <--> I
```

### ViewModel Responsibilities

| ViewModel | Responsibility | Used By |
| :--- | :--- | :--- |
| `MovieListViewModel` | Movie collections (`allMovies`, `watchedMovies`, `toWatchMovies`, `favoriteMovies`), `insert()`, `delete()`, database seeding & image fixes. | Dashboard, Movies, Library, Profile, AddMovie |
| `MovieDetailViewModel` | Single-movie operations and lookup (`allMovies`, `toggleFavorite()`, `toggleWatched()`, `addToWatch()`, `removeFromWatch()`, `saveReview()`, `update()`). | MovieDetail |

## 🗺️ Screen Map

The screen map presents the main navigation flow of the CineLog mobile application. The dashboard acts as the central hub, allowing users to move between movie details, the movie explorer, library views, the add movie form, and the user profile. Navigation uses `popUpTo(Dashboard)` with `launchSingleTop` to prevent infinite backstack stacking — the hardware back button always returns to the Dashboard from any tab.

<p align="center">
  <img src="screen-map.png" alt="CineLog Screen Map" width="900">
</p>

### Main Screens

| Screen | Description |
| :--- | :--- |
| `DashboardScreen` | Main home screen with featured movie, statistics, and recently added titles. |
| `MovieDetailScreen` | Detailed movie view with synopsis, rating, watch status, and user review. |
| `MoviesScreen` | Movie explorer with search, filters, sorting, and grid/list content. |
| `LibraryScreen` | Categorized lists: watched movies, to-watch titles, and favorites. |
| `AddMovieScreen` | Form for adding a new movie with metadata, poster URL, rating, and trailer URL. |
| `ProfileScreen` | User profile with movie statistics and genre distribution. |

### Key Architectural Pillars:
- **Reactive Streams**: Using `Kotlin Flow` and `StateFlow` for real-time UI updates.
- **Dependency Injection**: Constructor-based injection for the Repository and DAO.
- **Declarative UI**: 100% Jetpack Compose implementation with reusable stateless components.
- **Smart Navigation**: `popUpTo` + `launchSingleTop` + `restoreState` pattern prevents memory leaks from backstack stacking.

---

## 📊 Database Architecture

The application utilizes a high-performance **Room Persistence Library** (SQLite abstraction). The schema is designed for quick metadata retrieval and efficient filtering.

### Entity Relationship Diagram (ERD)

```mermaid
erDiagram
    MOVIES {
        int id PK "Auto-increment"
        string title "Movie Title"
        int year "Release Year"
        string genre "Category (Action, Drama, etc.)"
        double rating "Global/IMDb Score"
        int userRating "Personal Score (1-10)"
        string director "Director Name"
        string synopsis "Plot Summary"
        string poster "Poster Resource/URL"
        string backdrop "Hero Resource/URL"
        boolean watched "Watch Status Flag"
        boolean toWatch "Watchlist Flag"
        boolean favorite "Priority Flag"
        string review "User Opinion text"
        string trailerUrl "Video Source"
    }
```

### Data Schema Definition

| Field | Type | Constraint | Purpose |
| :--- | :--- | :--- | :--- |
| `id` | `Int` | `PRIMARY KEY` | Unique identifier for each entry. |
| `title` | `String` | `NOT NULL` | The primary display name. |
| `year` | `Int` | `DEFAULT 2024` | Temporal categorization. |
| `genre` | `String` | `INDEXED` | Facilitates multi-genre filtering. |
| `rating` | `Double` | `RANGE 0-10` | Reference quality score. |
| `userRating` | `Int` | `OPTIONAL` | Personalized qualitative metric. |
| `watched` | `Boolean` | `STATE` | Binary state for archive management. |
| `backdrop` | `String` | `URL/PATH` | Asset for immersive Hero UI components. |

---

## 🎨 UI/UX Philosophy

The interface is driven by a **Dark & Gold** design system, utilizing glassmorphism and high-contrast typography to create a "Cinematic Premium" feel.

### Component System
Located in `com.cinelog.ui.components`, our design system is modular:
- **`StatsComponents`**: Dynamic charts (Genre/Rating distributions) and Stat Cards.
- **`MovieComponents`**: Standardized cards (`MovieGridItem`, `MovieListItem`) and consistent headers.
- **Animations**: Leveraging `Crossfade` and `AnimatedVisibility` for fluid transitions.

---

## 🛠️ Technology Stack

- **Language**: Kotlin 1.9+
- **Asynchrony**: Coroutines, Flow, StateFlow.
- **Local DB**: Room (SQLite) - Version 8.
- **Image Engine**: Coil (with crossfade and error handling).
- **Navigation**: Compose Navigation with Type-Safe routes.
- **Video**: Internal Player with Hardware Acceleration.

---

## 🚀 Getting Started

1. **Prerequisites**: Android Studio Ladybug | JDK 17.
2. **Build**: Synced with Gradle 8.13.
3. **Seed**: The database automatically seeds with high-quality entries on first launch via `DatabaseInitializer`.

---

Developed with ❤️ for the Cinema Community.
