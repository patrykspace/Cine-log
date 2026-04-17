package com.cinelog.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.cinelog.data.AppDatabase
import com.cinelog.data.MovieRepository
import com.cinelog.ui.screens.*
import com.cinelog.ui.theme.CineLogTheme
import com.cinelog.viewmodel.MovieViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val database = AppDatabase.getDatabase(this)
        val repository = MovieRepository(database.movieDao())
        val viewModelFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MovieViewModel(repository) as T
            }
        }

        setContent {
            val viewModel: MovieViewModel = viewModel(factory = viewModelFactory)

            // Seed only once: check actual DB count in a coroutine so there's no
            // race condition with the Flow emitting before data is ready.
            LaunchedEffect(Unit) {
                viewModel.seedIfEmpty()
            }

            CineLogTheme {
                CineLogApp(viewModel)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CineLogApp(viewModel: MovieViewModel) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            
            // Only show bottom bar for main sections
            if (currentRoute in listOf(Screen.Dashboard.route, Screen.Movies.route, Screen.Library.route, Screen.Profile.route)) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == Screen.Dashboard.route,
                        onClick = { navController.navigate(Screen.Dashboard.route) },
                        icon = { Icon(Icons.Default.Home, contentDescription = null) },
                        label = { Text("Home") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == Screen.Movies.route,
                        onClick = { navController.navigate(Screen.Movies.route) },
                        icon = { Icon(Icons.Default.List, contentDescription = null) },
                        label = { Text("Movies") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == Screen.Library.route,
                        onClick = { navController.navigate(Screen.Library.route) },
                        icon = {
                            val toWatchCount = viewModel.toWatchMovies.collectAsState().value.size
                            BadgedBox(
                                badge = {
                                    if (toWatchCount > 0) {
                                        Badge { Text(toWatchCount.toString()) }
                                    }
                                }
                            ) {
                                Icon(Icons.Default.Star, contentDescription = null)
                            }
                        },
                        label = { Text("Library") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == Screen.Profile.route,
                        onClick = { navController.navigate(Screen.Profile.route) },
                        icon = { Icon(Icons.Default.Person, contentDescription = null) },
                        label = { Text("Profile") }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Dashboard.route) { DashboardScreen(navController, viewModel) }
            composable(Screen.Movies.route) { MoviesScreen(navController, viewModel) }
            composable(Screen.Library.route) { LibraryScreen(navController, viewModel) }
            composable(Screen.Profile.route) { ProfileScreen(navController, viewModel) }
            composable(
                route = Screen.MovieDetail.route,
                arguments = listOf(navArgument("movieId") { type = NavType.IntType })
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
                MovieDetailScreen(navController, viewModel, movieId)
            }
            composable(Screen.AddMovie.route) { AddMovieScreen(navController, viewModel) }
        }
    }
}
