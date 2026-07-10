package edu.metrostate.ics342.mediatracker.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import edu.metrostate.ics342.mediatracker.R
import edu.metrostate.ics342.mediatracker.data.FakeMediaRepository
import edu.metrostate.ics342.mediatracker.data.model.MediaDetail
import edu.metrostate.ics342.mediatracker.data.model.creatorCredit

// edu.metrostate.ics342.mediatracker.ui.detail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaDetailScreen(
    mediaId: Int,
    onNavigateBack: () -> Unit,
    onWriteReview: (Int) -> Unit
) {
    // Pull from FakeMediaRepository to ensure titles match the Search/Library screens.
    // Since we aren't doing network calls yet, we map the basic Media object to 
    // a MediaDetail object and mock the extra detail fields.
    val media = remember(mediaId) {
        FakeMediaRepository.mediaList.find { it.id == mediaId }?.let { m ->
            MediaDetail(
                id = m.id,
                mediaType = m.mediaType,
                title = m.title,
                author = m.author,
                director = m.director,
                creator = m.creator,
                network = m.network,
                coverUrl = m.coverUrl,
                publishedYear = m.publishedYear,
                averageRating = m.averageRating,
                ratingCount = m.ratingCount,
                genres = m.genres,
                description = "This is a detailed description for ${m.title}. It was originally released in ${m.publishedYear}. This placeholder text will be replaced by real API data in Week 9.",
                reviewCount = (m.ratingCount / 10),
                pageCount = if (m.mediaType == "book") 350 else null,
                runtimeMinutes = if (m.mediaType == "movie") 120 else null,
                seasonCount = if (m.mediaType == "show") 3 else null
            )
        } ?: MediaDetail(
            id = mediaId,
            mediaType = "movie",
            title = "Item $mediaId", // Better placeholder than "Inception"
            description = "Details for media ID $mediaId were not found in the fake repository.",
            averageRating = 0f,
            ratingCount = 0
        )
    }

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(media.title) }, // Added title to TopAppBar for clarity
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.action_back))
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Overflow menu */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = stringResource(R.string.action_more_options))
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Cover Image, large, centered
            AsyncImage(
                model = media.coverUrl,
                contentDescription = null,
                modifier = Modifier
                    .width(200.dp)
                    .height(300.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Title
            Text(
                text = media.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            // Creator Credit
            Text(
                text = media.creatorCredit(context),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Rating Row
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFC107), // Gold
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "%.1f".format(media.averageRating),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = " (${media.ratingCount})",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Two Buttons: + Want To (filled) and Save (outlined, heart icon)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { /* TODO */ },
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    Text("+ Want To")
                }
                OutlinedButton(
                    onClick = { /* TODO */ },
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Save")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // "About" label + description
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "About",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = media.description ?: "No description available.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.2
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 3-box stat grid: Year, Type-specific, Genre
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatBox(
                    label = "Year",
                    value = media.publishedYear?.toString() ?: "—"
                )
                StatBox(
                    label = when (media.mediaType) {
                        "book" -> "Pages"
                        "movie" -> "Runtime"
                        "show" -> "Seasons"
                        else -> "Info"
                    },
                    value = when (media.mediaType) {
                        "book" -> media.pageCount?.toString() ?: "—"
                        "movie" -> media.runtimeMinutes?.let { "${it}m" } ?: "—"
                        "show" -> media.seasonCount?.toString() ?: "—"
                        else -> "—"
                    }
                )
                StatBox(
                    label = "Genre",
                    value = media.genres.firstOrNull() ?: "—"
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // "Reviews (N)" row + "+ Write Review" button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Reviews (${media.reviewCount})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                TextButton(onClick = { onWriteReview(mediaId) }) {
                    Text("+ Write Review")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Review Cards
            SampleReviewCard(
                username = "brian_w",
                rating = 5,
                timestamp = "2 days ago",
                text = "Absolutely loved this! A must-see for everyone."
            )
            Spacer(modifier = Modifier.height(12.dp))
            SampleReviewCard(
                username = "jane_doe",
                rating = 4,
                timestamp = "1 week ago",
                text = "Really good, though some parts were a bit slow. Highly recommend the ending!"
            )
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun StatBox(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun SampleReviewCard(
    username: String,
    rating: Int,
    timestamp: String,
    text: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(32.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(username, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                    Text(timestamp, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Spacer(modifier = Modifier.weight(1f))
                Row {
                    repeat(rating) {
                        Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color(0xFFFFC107))
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text, style = MaterialTheme.typography.bodySmall)
        }
    }
}
