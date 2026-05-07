package com.cinelog.ui.screens

import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import com.cinelog.ui.components.GenreBarChart
import com.cinelog.ui.components.RatingBarChart
import com.cinelog.ui.components.StatMiniCard
import com.cinelog.viewmodel.MovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, viewModel: MovieViewModel) {
    val context = LocalContext.current
    val watchedMovies by viewModel.watchedMovies.collectAsState()
    val movies by viewModel.allMovies.collectAsState()
    
    // Grouping for the charts (only watched movies)
    val genreCounts = watchedMovies.groupBy { it.genre }.mapValues { it.value.size.toFloat() }
    val ratingCounts = movies.filter { it.userRating > 0 }.groupBy { it.userRating }.mapValues { it.value.size.toFloat() }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Cinema Profile", fontWeight = FontWeight.Bold) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // User Header
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "P", // User Initial
                    style = MaterialTheme.typography.displaySmall,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(Modifier.height(16.dp))
            
            Text("Patrick Moviegoer", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text("CineLog Member since 2024", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

            Spacer(Modifier.height(32.dp))

            // Stats Cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StatMiniCard("Total", movies.size.toString(), Modifier.weight(1f))
                StatMiniCard("Watched", watchedMovies.size.toString(), Modifier.weight(1f))
                StatMiniCard("Favs", movies.count { it.favorite }.toString(), Modifier.weight(1f))
            }

            Spacer(Modifier.height(32.dp))

            // Premium Chart Section - Genre
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            ) {
                Column(Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.BarChart, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(Modifier.width(8.dp))
                        Text("Genre Distribution (Watched)", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                    
                    Spacer(Modifier.height(24.dp))
                    
                    if (genreCounts.isNotEmpty()) {
                        GenreBarChart(genreCounts)
                    } else {
                        Text("Add watched movies to see stats", modifier = Modifier.padding(20.dp))
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Premium Chart Section - Ratings
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            ) {
                Column(Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.BarChart, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(Modifier.width(8.dp))
                        Text("Rating Distribution", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                    
                    Spacer(Modifier.height(24.dp))
                    
                    if (ratingCounts.isNotEmpty()) {
                        RatingBarChart(ratingCounts)
                    } else {
                        Text("Rate some movies to see stats", modifier = Modifier.padding(20.dp))
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Audio Player Section
            Text("Personal Soundtrack", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Start))
            Spacer(Modifier.height(12.dp))
            
            // MediaPlayer Logic
            val mediaPlayer = remember { MediaPlayer.create(context, com.cinelog.R.raw.soundtrack) }
            var isPlaying by remember { mutableStateOf(false) }
            
            DisposableEffect(Unit) {
                onDispose {
                    mediaPlayer.release()
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(Modifier.weight(1f)) {
                        Text("Mood: Cinematic", fontWeight = FontWeight.Bold)
                        Text(if (isPlaying) "Playing: soundtrack.mp3" else "Paused", style = MaterialTheme.typography.bodySmall)
                    }
                    IconButton(
                        onClick = { 
                            if (isPlaying) {
                                mediaPlayer.pause()
                            } else {
                                mediaPlayer.start()
                            }
                            isPlaying = !isPlaying
                        },
                        modifier = Modifier.background(MaterialTheme.colorScheme.primary, CircleShape)
                    ) {
                        Icon(
                            if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow, 
                            contentDescription = "Play/Pause", 
                            tint = Color.Black
                        )
                    }
                }
            }

            Spacer(Modifier.height(48.dp))
        }
    }
}

