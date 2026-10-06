package com.wadasanzo.colors.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wadasanzo.colors.data.repository.ColorRepository
import com.wadasanzo.colors.ui.screens.ColorDetailScreen
import com.wadasanzo.colors.ui.screens.ColorsScreen
import com.wadasanzo.colors.ui.screens.CombinationDetailScreen
import com.wadasanzo.colors.ui.screens.CombinationsScreen
import com.wadasanzo.colors.ui.screens.FavoritesScreen
import kotlinx.coroutines.launch

@Composable
fun WadaSanzoNavGraph(
    navController: NavHostController,
    repository: ColorRepository,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    // State for Colors list
    var colorSearchQuery by remember { mutableStateOf("") }
    var selectedSwatch by remember { mutableStateOf<Int?>(null) }

    // State for Combinations list
    var comboSearchQuery by remember { mutableStateOf("") }
    var selectedColorCount by remember { mutableStateOf<Int?>(null) }

    // Favorites from DataStore
    val favoriteColorIds by repository.getFavoriteColorIds().collectAsState(initial = emptySet())
    val favoriteComboIds by repository.getFavoriteComboIds().collectAsState(initial = emptySet())

    val filteredColors = remember(colorSearchQuery, selectedSwatch) {
        repository.searchColors(colorSearchQuery, selectedSwatch)
    }

    val filteredCombos = remember(comboSearchQuery, selectedColorCount) {
        repository.searchCombinations(comboSearchQuery, selectedColorCount)
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Colors.route,
        modifier = modifier.padding(paddingValues)
    ) {
        // Tab 1: Colors List
        composable(Screen.Colors.route) {
            ColorsScreen(
                colors = filteredColors,
                searchQuery = colorSearchQuery,
                onSearchQueryChange = { colorSearchQuery = it },
                selectedSwatch = selectedSwatch,
                onSwatchSelected = { selectedSwatch = it },
                favoriteColorIds = favoriteColorIds,
                onToggleFavoriteColor = { id ->
                    coroutineScope.launch { repository.toggleFavoriteColor(id) }
                },
                onColorClick = { colorId ->
                    navController.navigate(Screen.ColorDetail.createRoute(colorId))
                }
            )
        }

        // Tab 2: Combinations List
        composable(Screen.Combinations.route) {
            CombinationsScreen(
                combinations = filteredCombos,
                searchQuery = comboSearchQuery,
                onSearchQueryChange = { comboSearchQuery = it },
                selectedColorCount = selectedColorCount,
                onColorCountSelected = { selectedColorCount = it },
                favoriteComboIds = favoriteComboIds,
                onToggleFavoriteCombo = { id ->
                    coroutineScope.launch { repository.toggleFavoriteCombo(id) }
                },
                onCombinationClick = { comboId ->
                    navController.navigate(Screen.CombinationDetail.createRoute(comboId))
                },
                onColorClick = { colorId ->
                    navController.navigate(Screen.ColorDetail.createRoute(colorId))
                }
            )
        }

        // Tab 3: Favorites Screen
        composable(Screen.Favorites.route) {
            val favColors = remember(favoriteColorIds) {
                favoriteColorIds.mapNotNull { repository.getColorById(it) }
            }
            val favCombos = remember(favoriteComboIds) {
                favoriteComboIds.mapNotNull { repository.getCombinationById(it) }
            }

            FavoritesScreen(
                favoriteColors = favColors,
                favoriteCombinations = favCombos,
                onToggleFavoriteColor = { id ->
                    coroutineScope.launch { repository.toggleFavoriteColor(id) }
                },
                onToggleFavoriteCombo = { id ->
                    coroutineScope.launch { repository.toggleFavoriteCombo(id) }
                },
                onColorClick = { colorId ->
                    navController.navigate(Screen.ColorDetail.createRoute(colorId))
                },
                onCombinationClick = { comboId ->
                    navController.navigate(Screen.CombinationDetail.createRoute(comboId))
                }
            )
        }

        // Screen: Color Detail
        composable(
            route = Screen.ColorDetail.route,
            arguments = listOf(navArgument("colorId") { type = NavType.IntType })
        ) { backStackEntry ->
            val colorId = backStackEntry.arguments?.getInt("colorId") ?: 1
            val color = remember(colorId) { repository.getColorById(colorId) }
            val combinations = remember(colorId) { repository.getCombinationsForColor(colorId) }
            val isFavorite = favoriteColorIds.contains(colorId)

            ColorDetailScreen(
                color = color,
                combinations = combinations,
                isFavorite = isFavorite,
                favoriteComboIds = favoriteComboIds,
                onToggleFavorite = {
                    coroutineScope.launch { repository.toggleFavoriteColor(colorId) }
                },
                onToggleFavoriteCombo = { id ->
                    coroutineScope.launch { repository.toggleFavoriteCombo(id) }
                },
                onCombinationClick = { comboId ->
                    navController.navigate(Screen.CombinationDetail.createRoute(comboId))
                },
                onColorClick = { companionColorId ->
                    navController.navigate(Screen.ColorDetail.createRoute(companionColorId))
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        // Screen: Combination Detail
        composable(
            route = Screen.CombinationDetail.route,
            arguments = listOf(navArgument("comboId") { type = NavType.IntType })
        ) { backStackEntry ->
            val comboId = backStackEntry.arguments?.getInt("comboId") ?: 1
            val combo = remember(comboId) { repository.getCombinationById(comboId) }
            val isFavorite = favoriteComboIds.contains(comboId)

            CombinationDetailScreen(
                combination = combo,
                isFavorite = isFavorite,
                onToggleFavorite = {
                    coroutineScope.launch { repository.toggleFavoriteCombo(comboId) }
                },
                onColorClick = { colorId ->
                    navController.navigate(Screen.ColorDetail.createRoute(colorId))
                },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
