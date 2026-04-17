package com.cinelog.ui

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object Movies : Screen("movies")
    object Library : Screen("library")
    object Profile : Screen("profile")
    object MovieDetail : Screen("movieDetail/{movieId}") {
        fun createRoute(movieId: Int) = "movieDetail/$movieId"
    }
    object AddMovie : Screen("addMovie")
}
