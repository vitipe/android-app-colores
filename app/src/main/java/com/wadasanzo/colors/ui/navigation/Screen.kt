package com.wadasanzo.colors.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    data object Colors : Screen("colors")
    data object Combinations : Screen("combinations")
    data object Favorites : Screen("favorites")

    data object ColorDetail : Screen("color_detail/{colorId}") {
        fun createRoute(colorId: Int) = "color_detail/$colorId"
    }

    data object CombinationDetail : Screen("combination_detail/{comboId}") {
        fun createRoute(comboId: Int) = "combination_detail/$comboId"
    }
}

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    data object Colors : BottomNavItem(
        route = Screen.Colors.route,
        title = "Colors",
        selectedIcon = Icons.Filled.Palette,
        unselectedIcon = Icons.Outlined.Palette
    )

    data object Combinations : BottomNavItem(
        route = Screen.Combinations.route,
        title = "Combinations",
        selectedIcon = Icons.Filled.GridView,
        unselectedIcon = Icons.Outlined.GridView
    )

    data object Favorites : BottomNavItem(
        route = Screen.Favorites.route,
        title = "Favorites",
        selectedIcon = Icons.Filled.Favorite,
        unselectedIcon = Icons.Outlined.FavoriteBorder
    )
}
