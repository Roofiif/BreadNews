package com.dxid.breadnews.screens.save

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.dxid.breadnews.components.NewsSaveListScreen
import com.dxid.breadnews.screens.home.HomeViewModel
import com.dxid.breadnews.ui.theme.Orange
import com.dxid.breadnews.ui.theme.SoftOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveScreen(
    viewModel: HomeViewModel = hiltViewModel()
){
    val savedArticles by viewModel.getArticles.collectAsState()
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(
                    text = "Berita Tersimpan",
                    color = Orange,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                ) },
                colors = TopAppBarDefaults.topAppBarColors(Color.White)
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Orange, SoftOrange, Color.White)
                )
            ),
        containerColor = Color.Transparent,
    ) { padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            NewsSaveListScreen(newsList = savedArticles)
        }
    }
}