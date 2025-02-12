package com.dxid.breadnews.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    object Save : BottomNavItem("save", Icons.Default.Bookmark, "Save")

    companion object{
        fun bottomNavItems(): List<BottomNavItem>{
            return listOf(
                Home, Save,
            )
        }
    }
}