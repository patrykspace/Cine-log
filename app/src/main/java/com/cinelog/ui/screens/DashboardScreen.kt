package com.cinelog.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cinelog.ui.Screen
import com.cinelog.ui.components.FeaturedMovieCard
import com.cinelog.ui.components.MovieListItem
import com.cinelog.ui.components.SectionHeader
import com.cinelog.ui.components.StatCard
import com.cinelog.viewmodel.MovieListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController, listViewModel: MovieListViewModel) {
    val movies by listViewModel.allMovies.collectAsState()
    val watchedMovies by listViewModel.watchedMovies.collectAsState()
    val toWatchMovies by listViewModel.toWatchMovies.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddMovie.route) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Movie")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Column {
                    Text(
                        text = "Good Evening,",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "CineLog Dashboard",
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
            }

            // Featured Movie Section
            if (movies.isNotEmpty()) {
                item {
                    val featuredList = remember(movies) {
                        val targetedTitles = listOf("The Shawshank Redemption", "Inception", "Oppenheimer")
                        val targeted = movies.filter { it.title in targetedTitles }
                        if (targeted.isNotEmpty()) targeted else movies.sortedByDescending { it.rating }.take(3)
                    }
                    var currentIndex by remember { mutableIntStateOf(0) }

                    LaunchedEffect(featuredList) {
                        while (true) {
                            kotlinx.coroutines.delay(4000)
                            if (featuredList.isNotEmpty()) {
                                currentIndex = (currentIndex + 1) % featuredList.size
                            }
                        }
                    }

                    if (featuredList.isNotEmpty()) {
                        Crossfade(
                            targetState = featuredList[currentIndex],
                            animationSpec = androidx.compose.animation.core.tween(1000),
                            label = "featuredMovieFade"
                        ) { featured ->
                            FeaturedMovieCard(featured) {
                                navController.navigate(Screen.MovieDetail.createRoute(featured.id))
                            }
                        }
                    }
                }
            }

            item {
                StatsSection(watchedCount = watchedMovies.size, toWatchCount = toWatchMovies.size)
            }

            item {
                SectionHeader("Recently Added")
            }

            items(movies.take(5)) { movie ->
                MovieListItem(movie = movie) {
                    navController.navigate(Screen.MovieDetail.createRoute(movie.id))
                }
            }
        }
    }
}

@Composable
fun StatsSection(watchedCount: Int, toWatchCount: Int) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    AnimatedVisibility(
        visible = visible,
        enter = expandVertically() + fadeIn()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatCard(
                label = "Watched",
                value = watchedCount.toString(),
                icon = Icons.Default.Visibility,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                label = "To Watch",
                value = toWatchCount.toString(),
                icon = Icons.Default.Star,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

