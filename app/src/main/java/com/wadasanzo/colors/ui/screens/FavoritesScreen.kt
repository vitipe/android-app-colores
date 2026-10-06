package com.wadasanzo.colors.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wadasanzo.colors.data.model.ColorCombination
import com.wadasanzo.colors.data.model.SanzoColor
import com.wadasanzo.colors.ui.components.ColorListCard
import com.wadasanzo.colors.ui.components.CombinationCard

@Composable
fun FavoritesScreen(
    favoriteColors: List<SanzoColor>,
    favoriteCombinations: List<ColorCombination>,
    onToggleFavoriteColor: (Int) -> Unit,
    onToggleFavoriteCombo: (Int) -> Unit,
    onColorClick: (Int) -> Unit,
    onCombinationClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "SAVED",
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp,
                color = MaterialTheme.colorScheme.tertiary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Favorites",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        // Tabs
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Colors (${favoriteColors.size})", fontWeight = FontWeight.Medium) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Combinations (${favoriteCombinations.size})", fontWeight = FontWeight.Medium) }
            )
        }

        if (selectedTab == 0) {
            if (favoriteColors.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No favorite colors saved yet.\nTap the heart icon on any color to bookmark it.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.tertiary,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(favoriteColors, key = { it.id }) { color ->
                        ColorListCard(
                            color = color,
                            isFavorite = true,
                            onFavoriteClick = { onToggleFavoriteColor(color.id) },
                            onClick = { onColorClick(color.id) }
                        )
                    }
                }
            }
        } else {
            if (favoriteCombinations.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No favorite combinations saved yet.\nTap the heart icon on any combination to save it.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.tertiary,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(favoriteCombinations, key = { it.id }) { combo ->
                        CombinationCard(
                            combination = combo,
                            isFavorite = true,
                            onFavoriteClick = { onToggleFavoriteCombo(combo.id) },
                            onColorClick = { color -> onColorClick(color.id) },
                            onClick = { onCombinationClick(combo.id) }
                        )
                    }
                }
            }
        }
    }
}
