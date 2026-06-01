package com.cinelog.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cinelog.data.MovieEntity
import com.cinelog.data.MovieRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for **single-movie** interactions:
 * toggling watch/favorite status, saving reviews, and updating movie details.
 *
 * Used by: MovieDetailScreen (primary), and any screen with quick-action buttons.
 */
class MovieDetailViewModel(private val repository: MovieRepository) : ViewModel() {

    /** Read-only access to all movies — needed to look up a movie by ID on the detail screen. */
    val allMovies: StateFlow<List<MovieEntity>> = repository.allMovies
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun update(movie: MovieEntity) = viewModelScope.launch {
        repository.update(movie)
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
}
