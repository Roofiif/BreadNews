package com.dxid.breadnews.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.dxid.breadnews.data.models.Article
import com.dxid.breadnews.data.models.ArticleStore
import com.dxid.breadnews.util.formatDate

@Composable
fun Modifier.shimmerPlaceholder(): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "ShimmerAnimation")
    val shimmerX = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ShimmerXPosition"
    )
    val shimmerBrush = Brush.linearGradient(
        colors = listOf(
            Color.Gray.copy(alpha = 0.3f),
            Color.Gray.copy(alpha = 0.6f),
            Color.Gray.copy(alpha = 0.3f)
        ),
        start = Offset.Zero,
        end = Offset(shimmerX.value, shimmerX.value)
    )
    this.background(shimmerBrush)
}

@Composable
fun SnackBarComponent(snackbarHostState: SnackbarHostState, color: Color){
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ){
        SnackbarHost(
            hostState = snackbarHostState,
            snackbar = { snackbarData ->
                Snackbar(
                    snackbarData,
                    containerColor = color,
                    contentColor = Color.White,
                    actionOnNewLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)

                )
            }
        )
    }
}

@Composable
fun NewsCard (
    news: Article,
    onClick: () -> Unit,
    isLarge: Boolean,
    modifier: Modifier = Modifier
) {
    Card (
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(news.urlToImage)
                        .crossfade(true)
                        .build()
                ),
                contentDescription = news.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isLarge) 180.dp else 100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = (news.author ?: "") + " - " + (news.source.name ?: "Unknown Source"),
                fontSize = if (isLarge) 12.sp else 8.sp,
                color = Color.Black
            )

            Text(
                text = news.title ?: "No Title Available",
                fontSize = if (isLarge) 16.sp else 12.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = news.description ?: "No Description Available",
                fontSize = if (isLarge) 12.sp else 8.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = formatDate(news.publishedAt) ?: "Unknown Date",
                fontSize = if (isLarge) 12.sp else 8.sp,
                color = Color.Gray,
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
fun NewsSavedCard (
    news: ArticleStore,
    isLarge: Boolean,
    modifier: Modifier = Modifier
) {
    Card (
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(news.urlToImage)
                        .crossfade(true)
                        .build()
                ),
                contentDescription = news.url,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isLarge) 180.dp else 100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = news.title ?: "No Title Available",
                fontSize = if (isLarge) 16.sp else 12.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = news.url,
                fontSize = if (isLarge) 12.sp else 8.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Composable
fun NewsCardSkeleton(
    isLarge: Boolean,
    modifier: Modifier = Modifier
){
    Card (
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column (
            modifier = Modifier
                .padding(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isLarge) 180.dp else 100.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerPlaceholder()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isLarge) 16.dp else 8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerPlaceholder()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isLarge) 24.dp else 12.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerPlaceholder()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isLarge) 24.dp else 12.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerPlaceholder()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .width(if (isLarge) 150.dp else 50.dp)
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .align(Alignment.End)
                    .shimmerPlaceholder()
            )
        }
    }
}

@Composable
fun NewsListScreen(
    newsList: LazyPagingItems<Article>,
    searchQuery: String,
    onNewsClick: (Article) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val filteredNews = newsList.itemSnapshotList.items.filter { article ->
            article.title.orEmpty().contains(searchQuery, ignoreCase = true) ||
                    article.description.orEmpty().contains(searchQuery, ignoreCase = true)
        }
        if (filteredNews.isNotEmpty()) {
            // Pecah data menjadi kelompok 5
            filteredNews.chunked(5).forEach { chunk ->
                // Item pertama dalam grup besar
                item {
                    NewsCard(news = chunk[0], onClick = { onNewsClick(chunk[0]) }, isLarge = true)
                }

                // Sisanya kecil dalam baris berisi 2 item
                items(chunk.drop(1).chunked(2)) { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowItems.forEach { news ->
                            NewsCard(news = news, onClick = { onNewsClick(news) }, isLarge = false, Modifier.weight(1f))
                        }
                        // Jika jumlah item ganjil, tambahkan spacer agar tetap rata
                        if (rowItems.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
        newsList.apply {
            when (loadState.append) {
                is LoadState.Loading -> {
                    item { CircularProgressIndicator(modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)) }
                }

                is LoadState.Error -> {
                    val error = (loadState.append as LoadState.Error).error
                    item {
                        Text(
                            text = "Gagal memuat berita: ${error.localizedMessage}",
                            color = Color.Red,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                else -> {Unit}
            }
        }
    }
}

@Composable
fun NewsSaveListScreen(
    newsList: List<ArticleStore>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (newsList.isNotEmpty()) {
            newsList.chunked(5).forEach { chunk ->
                item {
                    NewsSavedCard(news = chunk[0], isLarge = true)
                }

                items(chunk.drop(1).chunked(2)) { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowItems.forEach { news ->
                            NewsSavedCard(news = news, isLarge = false, Modifier.weight(1f))
                        }
                        if (rowItems.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NewsListSkeleton() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        NewsCardSkeleton(isLarge = true)
        repeat(2) { // Simulasi 2 kelompok berita
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                NewsCardSkeleton(isLarge = false, Modifier.weight(1f))
                NewsCardSkeleton(isLarge = false, Modifier.weight(1f))
            }
        }
        NewsCardSkeleton(isLarge = true)
        repeat(2) { // Simulasi 2 kelompok berita
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                NewsCardSkeleton(isLarge = false, Modifier.weight(1f))
                NewsCardSkeleton(isLarge = false, Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChanged,
        placeholder = { Text("Cari Berita...", color = Color.White) },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = Color.White)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(25.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.White,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            cursorColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
        )
    )
}
