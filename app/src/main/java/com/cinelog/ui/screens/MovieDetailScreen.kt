package com.cinelog.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cinelog.viewmodel.MovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(navController: NavController, viewModel: MovieViewModel, movieId: Int) {
    val movies by viewModel.allMovies.collectAsState()
    val movie = movies.find { it.id == movieId } ?: return

    val context = LocalContext.current

    // Local state for editable review
    var userRating by remember(movie.userRating) { mutableIntStateOf(movie.userRating) }
    var reviewText by remember(movie.review) { mutableStateOf(movie.review) }
    var reviewEditing by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Black.copy(alpha = 0.5f))
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.toggleFavorite(movie) },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Black.copy(alpha = 0.5f))
                    ) {
                        Icon(
                            if (movie.favorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (movie.favorite) MaterialTheme.colorScheme.primary else Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Backdrop Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                var showTrailer by remember { mutableStateOf(false) }
                var videoViewInstance by remember { mutableStateOf<android.widget.VideoView?>(null) }

                DisposableEffect(Unit) {
                    onDispose {
                        videoViewInstance?.stopPlayback()
                    }
                }

                if (showTrailer && movie.trailerUrl.isNotBlank()) {
                    if (movie.trailerUrl == "local_sample" || movie.trailerUrl.startsWith("res/")) {
                        // Local Video Player
                        androidx.compose.ui.viewinterop.AndroidView(
                            factory = { ctx ->
                                android.widget.VideoView(ctx).apply {
                                    videoViewInstance = this
                                    val videoName = if (movie.trailerUrl == "local_sample") "sample_video" else movie.trailerUrl.substringAfter("res/")
                                    val videoId = ctx.resources.getIdentifier(videoName, "raw", ctx.packageName)
                                    if (videoId != 0) {
                                        val videoUri = android.net.Uri.parse("android.resource://${ctx.packageName}/$videoId")
                                        setVideoURI(videoUri)
                                        val mc = android.widget.MediaController(ctx)
                                        mc.setAnchorView(this)
                                        setMediaController(mc)
                                        setOnPreparedListener { mp ->
                                            mp.isLooping = false
                                            start()
                                        }
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        // YouTube Player
                        val videoId = if (movie.trailerUrl.contains("v=")) {
                            movie.trailerUrl.substringAfter("v=").substringBefore("&")
                        } else if (movie.trailerUrl.contains("youtu.be/")) {
                            movie.trailerUrl.substringAfter("youtu.be/").substringBefore("?")
                        } else {
                            movie.trailerUrl.substringAfterLast("/")
                        }
                        val embedUrl = "https://www.youtube.com/embed/$videoId?autoplay=1&modestbranding=1"
                        
                        androidx.compose.ui.viewinterop.AndroidView(
                            factory = { ctx ->
                                android.webkit.WebView(ctx).apply {
                                    settings.javaScriptEnabled = true
                                    settings.loadWithOverviewMode = true
                                    settings.useWideViewPort = true
                                    settings.domStorageEnabled = true
                                    settings.mediaPlaybackRequiresUserGesture = false // Allow autoplay
                                    setBackgroundColor(0x00000000) // Transparent background to avoid white flash
                                    webViewClient = android.webkit.WebViewClient()
                                    webChromeClient = android.webkit.WebChromeClient()
                                    loadUrl(embedUrl)
                                }
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                } else {
                    if (movie.backdrop.isNotBlank()) {
                        val resourceId = context.resources.getIdentifier(movie.backdrop, "drawable", context.packageName)

                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(if (resourceId != 0) resourceId else movie.backdrop)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    if (movie.trailerUrl.isNotBlank()) {
                        IconButton(
                            onClick = { showTrailer = true },
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(64.dp)
                                .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                        ) {
                            Icon(
                                Icons.Default.PlayArrow,
                                contentDescription = "Play Trailer",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                        Text(
                            "Watch Trailer",
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.align(Alignment.Center).padding(top = 80.dp)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, MaterialTheme.colorScheme.background),
                                startY = 400f
                            )
                        )
                )

                // Poster overlay
                Card(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 20.dp, bottom = 0.dp)
                        .size(100.dp, 150.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    val posterRes = context.resources.getIdentifier(movie.poster, "drawable", context.packageName)
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(if (posterRes != 0) posterRes else movie.poster)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Column(Modifier.padding(20.dp)) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                    Text(
                        text = " ${movie.rating} ",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text("• ${movie.year} • ${movie.genre}", style = MaterialTheme.typography.bodyMedium)
                }

                Spacer(Modifier.height(24.dp))

                Text(text = "Synopsis", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text(
                    text = movie.synopsis,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 12.dp),
                    lineHeight = 26.sp
                )

                Spacer(Modifier.height(8.dp))

                // ── Watch / To Watch Action Buttons ──────────────────────────────
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Watched button
                    Button(
                        onClick = { viewModel.toggleWatched(movie) },
                        modifier = Modifier.weight(1f).height(52.dp),
                        colors = if (!movie.watched)
                            ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        else
                            ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Icon(
                            if (!movie.watched) Icons.Default.CheckCircle else Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = if (!movie.watched) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            if (movie.watched) "Watched" else "Mark Watched",
                            fontWeight = FontWeight.Bold,
                            color = if (!movie.watched) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Add to Watch List button
                    OutlinedButton(
                        onClick = {
                            if (movie.toWatch) viewModel.removeFromWatch(movie)
                            else viewModel.addToWatch(movie)
                        },
                        modifier = Modifier.weight(1f).height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = if (movie.toWatch)
                            ButtonDefaults.outlinedButtonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                        else
                            ButtonDefaults.outlinedButtonColors()
                    ) {
                        Icon(
                            if (movie.toWatch) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            if (movie.toWatch) "In Watchlist" else "Add to List",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 13.sp
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                // ── Review & Rating Section (always visible) ─────────────────────
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.RateReview,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(Modifier.width(8.dp))
                            Text("My Review", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                            if (!reviewEditing) {
                                IconButton(onClick = { reviewEditing = true }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary)
                                }
                            }
                        }

                        Spacer(Modifier.height(12.dp))

                        // Star Rating Row
                        Text("Rating: $userRating / 10", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(Modifier.height(6.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            (1..10).forEach { star ->
                                IconButton(
                                    onClick = {
                                        userRating = star
                                        reviewEditing = true
                                    },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        if (star <= userRating) Icons.Default.Star else Icons.Default.StarBorder,
                                        contentDescription = "$star stars",
                                        tint = if (star <= userRating) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            }
                        }

                        Spacer(Modifier.height(12.dp))

                        if (reviewEditing) {
                            OutlinedTextField(
                                value = reviewText,
                                onValueChange = { reviewText = it },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("Write your review...", color = MaterialTheme.colorScheme.onSurfaceVariant) },
                                minLines = 3,
                                maxLines = 6,
                                shape = RoundedCornerShape(12.dp),
                                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                                label = { Text("Review") }
                            )
                            Spacer(Modifier.height(12.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedButton(
                                    onClick = {
                                        userRating = movie.userRating
                                        reviewText = movie.review
                                        reviewEditing = false
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Cancel")
                                }
                                Button(
                                    onClick = {
                                        viewModel.saveReview(movie, userRating, reviewText)
                                        reviewEditing = false
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Save", fontWeight = FontWeight.Bold)
                                }
                            }
                        } else {
                            Text(
                                text = if (reviewText.isNotBlank()) reviewText else "Tap ✏️ to write a review.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (reviewText.isNotBlank()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = if (reviewText.isNotBlank()) FontWeight.Normal else FontWeight.Light
                            )
                        }
                    }
                }

                Spacer(Modifier.height(32.dp))
            }
        }
    }
}
