package com.cinelog.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val year: Int,
    val genre: String,
    val rating: Double,
    val userRating: Int = 0,
    val director: String,
    val synopsis: String,
    val poster: String, // URL or resource string
    val backdrop: String,
    val watched: Boolean = false,
    val toWatch: Boolean = false,
    val favorite: Boolean = false,
    val review: String = "",
    val runtime: String = "",
    val trailerUrl: String = ""
)
