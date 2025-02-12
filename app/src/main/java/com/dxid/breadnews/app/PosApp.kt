package com.dxid.breadnews.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dxid.breadnews.components.graph.RootGraph
import com.dxid.breadnews.screens.MainScreen
import com.dxid.breadnews.screens.detailarticle.ArticleScreen
import java.net.URLEncoder

@Composable
fun PosApp() {
    val rootNavController = rememberNavController() // Root NavController

    NavHost(
        navController = rootNavController,
        startDestination = RootGraph.MainScreen.route
    ) {
        composable(
            route = RootGraph.MainScreen.route,
            content = {
                MainScreen(
                    onNavigateToDetail = { articleUrl ->
                        val encodedUrl = URLEncoder.encode(articleUrl, "UTF-8")
                        rootNavController.navigate(RootGraph.DetailScreen.route + "/$encodedUrl")
                    }
                )
            }
        )
        composable(
            route = RootGraph.DetailScreen.route + "/{articleUrl}",
            arguments = listOf(navArgument("articleUrl") { type = NavType.StringType })
        ) { backStackEntry ->
            val articleUrl = backStackEntry.arguments?.getString("articleUrl") ?: ""
            ArticleScreen(
                articleUrl = articleUrl,
                onBackPressed = { rootNavController.popBackStack() }
            )
        }
       }
}