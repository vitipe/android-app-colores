package com.wadasanzo.colors.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SwatchBookFilterRow(
    selectedSwatch: Int?,
    onSwatchSelected: (Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip(
            selected = selectedSwatch == null,
            onClick = { onSwatchSelected(null) },
            label = { Text("All Books", fontSize = 12.sp) },
            shape = RoundedCornerShape(8.dp),
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.primary,
                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
            )
        )

        for (book in 1..6) {
            val swatchIndex = book - 1
            FilterChip(
                selected = selectedSwatch == swatchIndex,
                onClick = {
                    onSwatchSelected(if (selectedSwatch == swatchIndex) null else swatchIndex)
                },
                label = { Text("Book $book", fontSize = 12.sp) },
                shape = RoundedCornerShape(8.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}

@Composable
fun CombinationTypeFilterRow(
    selectedCount: Int?,
    onCountSelected: (Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip(
            selected = selectedCount == null,
            onClick = { onCountSelected(null) },
            label = { Text("All Palettes (348)", fontSize = 12.sp) },
            shape = RoundedCornerShape(8.dp),
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.primary,
                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
            )
        )

        FilterChip(
            selected = selectedCount == 2,
            onClick = { onCountSelected(if (selectedCount == 2) null else 2) },
            label = { Text("2 Colors (120)", fontSize = 12.sp) },
            shape = RoundedCornerShape(8.dp),
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.primary,
                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
            )
        )

        FilterChip(
            selected = selectedCount == 3,
            onClick = { onCountSelected(if (selectedCount == 3) null else 3) },
            label = { Text("3 Colors (120)", fontSize = 12.sp) },
            shape = RoundedCornerShape(8.dp),
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.primary,
                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
            )
        )

        FilterChip(
            selected = selectedCount == 4,
            onClick = { onCountSelected(if (selectedCount == 4) null else 4) },
            label = { Text("4 Colors (108)", fontSize = 12.sp) },
            shape = RoundedCornerShape(8.dp),
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.primary,
                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
            )
        )
    }
}
