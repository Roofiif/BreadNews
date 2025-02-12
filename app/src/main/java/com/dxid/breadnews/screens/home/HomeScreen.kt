package com.dxid.breadnews.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.dxid.breadnews.components.NewsListScreen
import com.dxid.breadnews.components.NewsListSkeleton
import com.dxid.breadnews.components.SearchBar
import com.dxid.breadnews.components.SnackBarComponent
import com.dxid.breadnews.data.models.ArticleStore
import com.dxid.breadnews.ui.theme.Orange
import com.dxid.breadnews.ui.theme.SoftOrange

@Composable
fun HomeScreen(
    onNewsClick: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
){
    val snackbarHostState = remember { SnackbarHostState() }
    val newsList = viewModel.newsPagingFlow.collectAsLazyPagingItems()
    val error = viewModel.transactionError.collectAsStateWithLifecycle().value
    val isLoading = newsList.loadState.refresh is LoadState.Loading
    var searchQuery by remember { mutableStateOf("") }

    Scaffold (
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Orange, SoftOrange, Color.White)
                )
            ),
        containerColor = Color.Transparent,
        snackbarHost = { SnackBarComponent(snackbarHostState = snackbarHostState, color = Color.DarkGray) }
    ) { padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            SearchBar(searchQuery) { query -> searchQuery = query }

            if (isLoading){
                NewsListSkeleton()
            }else{
                NewsListScreen(
                    newsList = newsList,
                    searchQuery = searchQuery,
                    onNewsClick = { article ->
                        val savedArticle = ArticleStore(
                            urlToImage = article.urlToImage,
                            url = article.url,
                            title = article.title,
                        )
                        viewModel.saveArticle(savedArticle)
                        onNewsClick(article.url)
                    }
                )
            }
            error?.let {
                LaunchedEffect(snackbarHostState) {
                    snackbarHostState.showSnackbar(
                        message = it,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }
}