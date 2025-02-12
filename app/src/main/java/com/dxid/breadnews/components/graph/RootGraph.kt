package com.dxid.breadnews.components.graph


sealed class RootGraph(val route: String, val label: String) {
    object MainScreen : RootGraph("main_screen", "Main_Screen")
    object DetailScreen : RootGraph("detail_screen" ,"Detail_Screen")
}