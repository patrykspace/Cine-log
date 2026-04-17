package com.cinelog.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cinelog.ui.Screen
import com.cinelog.ui.components.MovieListItem
import com.cinelog.viewmodel.MovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(navController: NavController, viewModel: MovieViewModel) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Watched", "To Watch", "Favorites")

    val watchedMovies by viewModel.watchedMovies.collectAsState()
    val toWatchMovies by viewModel.toWatchMovies.collectAsState()
    val favoriteMovies by viewModel.favoriteMovies.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }

        val moviesToShow = when (selectedTabIndex) {
            0 -> watchedMovies
            1 -> toWatchMovies
            2 -> favoriteMovies
            else -> emptyList()
        }

        if (moviesToShow.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("No movies in this list yet.")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(moviesToShow, key = { it.id }) { movie ->
                    MovieListItem(movie = movie) {
                        navController.navigate(Screen.MovieDetail.createRoute(movie.id))
                    }
                }
            }
        }
    }
}
