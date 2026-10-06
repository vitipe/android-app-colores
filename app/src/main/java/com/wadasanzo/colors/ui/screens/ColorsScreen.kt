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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wadasanzo.colors.data.model.SanzoColor
import com.wadasanzo.colors.ui.components.ColorGridCard
import com.wadasanzo.colors.ui.components.ColorListCard
import com.wadasanzo.colors.ui.components.SanzoSearchBar
import com.wadasanzo.colors.ui.components.SwatchBookFilterRow

@Composable
fun ColorsScreen(
    colors: List<SanzoColor>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedSwatch: Int?,
    onSwatchSelected: (Int?) -> Unit,
    favoriteColorIds: Set<Int>,
    onToggleFavoriteColor: (Int) -> Unit,
    onColorClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var isGridView by remember { mutableStateOf(true) }

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
                text = "Color Dictionary",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Search Bar
            SanzoSearchBar(
                query = searchQuery,
                onQueryChange = onSearchQueryChange,
                placeholder = "Search 159 colors, hex..."
            )
        }

        // Swatch Book Filters & View Toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1f)) {
                SwatchBookFilterRow(
                    selectedSwatch = selectedSwatch,
                    onSwatchSelected = onSwatchSelected
                )
            }
            IconButton(
                onClick = { isGridView = !isGridView },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = if (isGridView) Icons.Default.ViewList else Icons.Default.GridView,
                    contentDescription = "Toggle View",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        }

        // Count indicator
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${colors.size} colors found",
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.tertiary
            )
            if (selectedSwatch != null) {
                Text(
                    text = "Book ${selectedSwatch + 1}",
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }

        // Color Items
        if (colors.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No colors match your search",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        } else if (isGridView) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(colors, key = { it.id }) { color ->
                    ColorGridCard(
                        color = color,
                        isFavorite = favoriteColorIds.contains(color.id),
                        onFavoriteClick = { onToggleFavoriteColor(color.id) },
                        onClick = { onColorClick(color.id) }
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(colors, key = { it.id }) { color ->
                    ColorListCard(
                        color = color,
                        isFavorite = favoriteColorIds.contains(color.id),
                        onFavoriteClick = { onToggleFavoriteColor(color.id) },
                        onClick = { onColorClick(color.id) }
                    )
                }
            }
        }
    }
}
