package com.cinelog.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cinelog.data.MovieEntity
import com.cinelog.data.MovieRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    val allMovies: StateFlow<List<MovieEntity>> = repository.allMovies
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val watchedMovies: StateFlow<List<MovieEntity>> = repository.watchedMovies
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val toWatchMovies: StateFlow<List<MovieEntity>> = repository.toWatchMovies
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val favoriteMovies: StateFlow<List<MovieEntity>> = repository.favoriteMovies
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun insert(movie: MovieEntity) = viewModelScope.launch {
        repository.insert(movie)
    }

    fun update(movie: MovieEntity) = viewModelScope.launch {
        repository.update(movie)
    }

    fun delete(movie: MovieEntity) = viewModelScope.launch {
        repository.delete(movie)
    }

    fun toggleFavorite(movie: MovieEntity) = viewModelScope.launch {
        repository.update(movie.copy(favorite = !movie.favorite))
    }

    fun toggleWatched(movie: MovieEntity) = viewModelScope.launch {
        if (!movie.watched) {
            // When marking as watched, remove from toWatch
            repository.update(movie.copy(watched = true, toWatch = false))
        } else {
            // Unmarking as watched
            repository.update(movie.copy(watched = false))
        }
    }

    fun addToWatch(movie: MovieEntity) = viewModelScope.launch {
        repository.update(movie.copy(toWatch = true, watched = false))
    }

    fun removeFromWatch(movie: MovieEntity) = viewModelScope.launch {
        repository.update(movie.copy(toWatch = false))
    }

    fun saveReview(movie: MovieEntity, rating: Int, review: String) = viewModelScope.launch {
        repository.update(movie.copy(userRating = rating, review = review))
    }

    /** Ensures specific movies have the correct artwork even if they were already in the DB. */
    fun fixSpecificMoviesImages() = viewModelScope.launch {
        val updates = listOf(
            // Godfather
            Triple("%Godfather%", "https://media.posterlounge.com/img/products/710000/707663/707663_poster.jpg", "https://image.tmdb.org/t/p/w780/tmU7GeKVZ2pSdhL70xJu0ZDs99t.jpg"),
            Triple("%God father%", "https://media.posterlounge.com/img/products/710000/707663/707663_poster.jpg", "https://image.tmdb.org/t/p/w780/tmU7GeKVZ2pSdhL70xJu0ZDs99t.jpg"),
            // Gone Girl (Backdrop REMOVED as requested)
            Triple("%Gone Girl%", "https://play-lh.googleusercontent.com/IIz8p1DWmLveduLGKd69zRWG7xssvOjYzLMEbzrDo-N5sgfi_ZxZu1l7TbTgkLsi014zcoPrDZQVr3g7H4s", ""),
            // Dark Knight
            Triple("%Dark Knight%", "https://play-lh.googleusercontent.com/m6LAGUVG2BURUJ1ziMQFtYzWadIcuV6WHMBwhf5qO3ujN8EtIp94J99YEYaR0BfiH7fa", "https://image.tmdb.org/t/p/w780/oXUunYhnunpS2SfsUvB7Te97XbW.jpg"),
            Triple("%Batman%", "https://play-lh.googleusercontent.com/m6LAGUVG2BURUJ1ziMQFtYzWadIcuV6WHMBwhf5qO3ujN8EtIp94J99YEYaR0BfiH7fa", "https://image.tmdb.org/t/p/w780/oXUunYhnunpS2SfsUvB7Te97XbW.jpg"),
            // No Country 
            Triple("%No Country%", "https://images.squarespace-cdn.com/content/v1/5e02ce8fbe14ca5d06039aca/1596384669439-PFPIFUCMCGI38C2JD9KD/ncfom+1.jpg?format=750w", "https://image.tmdb.org/t/p/w780/yYh6pP82oHn5FfM647rOms91KTo.jpg"),
            Triple("%Old man%", "https://images.squarespace-cdn.com/content/v1/5e02ce8fbe14ca5d06039aca/1596384669439-PFPIFUCMCGI38C2JD9KD/ncfom+1.jpg?format=750w", "https://image.tmdb.org/t/p/w780/yYh6pP82oHn5FfM647rOms91KTo.jpg")
        )
        
        updates.forEach { (pattern, poster, backdrop) ->
            repository.updateImages(pattern, poster, backdrop)
        }
        // Force clear all other backdrops except the allowed ones
        repository.clearOtherBackdrops()
    }

    /** Clears the database and re-seeds it from scratch. */
    fun resetDatabase() = viewModelScope.launch {
        repository.deleteAll()
        com.cinelog.data.DatabaseInitializer.seedDatabase(this@MovieViewModel)
        repository.clearOtherBackdrops()
    }

    /** Called once at startup – seeds the database only if it is empty. */
    fun seedIfEmpty() = viewModelScope.launch {
        if (repository.getMovieCount() == 0) {
            com.cinelog.data.DatabaseInitializer.seedDatabase(this@MovieViewModel)
        }
        // Always run fix to ensure latest URLs are applied to existing movies
        fixSpecificMoviesImages()
    }
}
