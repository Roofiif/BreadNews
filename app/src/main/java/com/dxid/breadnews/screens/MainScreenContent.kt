package com.dxid.breadnews.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dxid.breadnews.components.BottomNavBar
import com.dxid.breadnews.components.BottomNavItem
import com.dxid.breadnews.screens.home.HomeScreen
import com.dxid.breadnews.screens.save.SaveScreen

@Composable
fun MainScreen(
    onNavigateToDetail: (String) -> Unit
) {
    val bottomNavController = rememberNavController() // Separate NavController

    Scaffold(
        bottomBar = {
            BottomNavBar(bottomNavController)
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
            navController = bottomNavController,
            startDestination = BottomNavItem.Home.route
        ) {
            composable(BottomNavItem.Home.route) {
                HomeScreen(
                    onNewsClick = { articleUrl ->
                        onNavigateToDetail(articleUrl) // Use root navigation
                    }
                )
            }
            composable(BottomNavItem.Save.route) { SaveScreen() }
        }
    }
}