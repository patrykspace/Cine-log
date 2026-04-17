package com.cinelog.data

import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDao: MovieDao) {
    val allMovies: Flow<List<MovieEntity>> = movieDao.getAllMovies()
    val watchedMovies: Flow<List<MovieEntity>> = movieDao.getWatchedMovies()
    val toWatchMovies: Flow<List<MovieEntity>> = movieDao.getToWatchMovies()
    val favoriteMovies: Flow<List<MovieEntity>> = movieDao.getFavoriteMovies()

    suspend fun insert(movie: MovieEntity) {
        movieDao.insertMovie(movie)
    }

    suspend fun update(movie: MovieEntity) {
        movieDao.updateMovie(movie)
    }

    suspend fun delete(movie: MovieEntity) {
        movieDao.deleteMovie(movie)
    }

    suspend fun getMovieById(id: Int): MovieEntity? {
        return movieDao.getMovieById(id)
    }

    suspend fun getMovieCount(): Int {
        return movieDao.getMovieCount()
    }
    suspend fun updateImages(titlePattern: String, poster: String, backdrop: String) {
        movieDao.updateMovieImages(titlePattern, poster, backdrop)
    }

    suspend fun deleteAll() {
        movieDao.deleteAll()
    }

    suspend fun clearOtherBackdrops() {
        movieDao.clearOtherBackdrops()
    }
}
