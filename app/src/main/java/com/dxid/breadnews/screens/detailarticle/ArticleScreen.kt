package com.dxid.breadnews.screens.detailarticle

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.dxid.breadnews.ui.theme.Orange


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen(articleUrl: String, onBackPressed: () -> Unit) {
    val context = LocalContext.current
    val webView = remember { WebView(context) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Berita", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = {
                        if (webView.canGoBack()) {
                            webView.goBack() // Navigate back within the WebView
                        } else {
                            onBackPressed() // Close the screen
                        }
                    }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(Orange)
            )
        },
    ) { paddingValues ->
        AndroidView(
            factory = { webView },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) { view ->
            view.webViewClient = WebViewClient()
            view.settings.javaScriptEnabled = false
            view.loadUrl(articleUrl)
        }
    }
}