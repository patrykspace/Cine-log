package com.cinelog.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("SELECT COUNT(*) FROM movies")
    suspend fun getMovieCount(): Int

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovieById(id: Int): MovieEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Update
    suspend fun updateMovie(movie: MovieEntity)

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)

    @Query("SELECT * FROM movies WHERE watched = 1")
    fun getWatchedMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE toWatch = 1")
    fun getToWatchMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE favorite = 1")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>

    @Query("UPDATE movies SET poster = :poster, backdrop = :backdrop WHERE LOWER(title) LIKE LOWER(:titlePattern)")
    suspend fun updateMovieImages(titlePattern: String, poster: String, backdrop: String)

    @Query("DELETE FROM movies")
    suspend fun deleteAll()

    @Query("UPDATE movies SET backdrop = '' WHERE title NOT IN ('The Shawshank Redemption', 'Inception', 'Oppenheimer')")
    suspend fun clearOtherBackdrops()
}
