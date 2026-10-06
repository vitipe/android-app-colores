package com.wadasanzo.colors.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wadasanzo.colors.data.model.ColorCombination
import com.wadasanzo.colors.ui.components.CombinationCard
import com.wadasanzo.colors.ui.components.CombinationTypeFilterRow
import com.wadasanzo.colors.ui.components.SanzoSearchBar

@Composable
fun CombinationsScreen(
    combinations: List<ColorCombination>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedColorCount: Int?,
    onColorCountSelected: (Int?) -> Unit,
    favoriteComboIds: Set<Int>,
    onToggleFavoriteCombo: (Int) -> Unit,
    onCombinationClick: (Int) -> Unit,
    onColorClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
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
                text = "WADA SANZO",
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp,
                color = MaterialTheme.colorScheme.tertiary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Color Combinations",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Search Bar
            SanzoSearchBar(
                query = searchQuery,
                onQueryChange = onSearchQueryChange,
                placeholder = "Search combinations by #, color..."
            )
        }

        // Color Count Filter Row (All, 2, 3, 4)
        CombinationTypeFilterRow(
            selectedCount = selectedColorCount,
            onCountSelected = onColorCountSelected,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Count indicator
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${combinations.size} combinations",
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.tertiary
            )
        }

        // List of Combinations
        if (combinations.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No combinations found",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(combinations, key = { it.id }) { combo ->
                    CombinationCard(
                        combination = combo,
                        isFavorite = favoriteComboIds.contains(combo.id),
                        onFavoriteClick = { onToggleFavoriteCombo(combo.id) },
                        onColorClick = { color -> onColorClick(color.id) },
                        onClick = { onCombinationClick(combo.id) }
                    )
                }
            }
        }
    }
}
