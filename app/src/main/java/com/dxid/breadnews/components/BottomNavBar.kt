package com.dxid.breadnews.components

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dxid.breadnews.components.BottomNavItem.Companion.bottomNavItems
import com.dxid.breadnews.ui.theme.Orange

@Composable
fun BottomNavBar(
    navController : NavController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomAppBar (
        containerColor = Color.White,
    ) {
        NavigationBar (
            containerColor = Color.White,
            contentColor = Color.White
        ) {
            bottomNavItems().forEachIndexed { _, bottomNavItem ->
                val isSelected = currentRoute == bottomNavItem.route
                NavigationBarItem(
                    selected = isSelected,
                    label = { Text(
                        bottomNavItem.label,
                        color = Orange
                    ) },
                    icon = {
                        Icon(
                            imageVector = bottomNavItem.icon,
                            contentDescription = bottomNavItem.label,
                            tint = if (isSelected) Color.White else Orange
                        )
                    },
                    onClick = {
                        navController.navigate(bottomNavItem.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = if (isSelected) Orange else Color.White,
                    )
                )
            }
        }
    }
}