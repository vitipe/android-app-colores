package com.wadasanzo.colors.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wadasanzo.colors.data.model.ColorCombination
import com.wadasanzo.colors.data.model.SanzoColor
import com.wadasanzo.colors.ui.components.ColorValuesSection
import com.wadasanzo.colors.ui.components.CombinationCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorDetailScreen(
    color: SanzoColor?,
    combinations: List<ColorCombination>,
    isFavorite: Boolean,
    favoriteComboIds: Set<Int>,
    onToggleFavorite: () -> Unit,
    onToggleFavoriteCombo: (Int) -> Unit,
    onCombinationClick: (Int) -> Unit,
    onColorClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    if (color == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Color not found", style = MaterialTheme.typography.bodyLarge)
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = color.name,
                        maxLines = 1,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onToggleFavorite) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isFavorite) Color(0xFFE53935) else MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Large Hero Swatch
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                            RoundedCornerShape(16.dp)
                        ),
                    color = color.composeColor
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp)
                    ) {
                        // Top badge: Swatch Book
                        Text(
                            text = color.swatchBookName.uppercase(),
                            color = color.textColor.copy(alpha = 0.8f),
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .background(
                                    color = color.composeColor.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 6.dp, vertical = 3.dp)
                        )

                        // Bottom info: Name and Hex
                        Column(modifier = Modifier.align(Alignment.BottomStart)) {
                            Text(
                                text = color.name,
                                color = color.textColor,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = color.hex.uppercase(),
                                color = color.textColor.copy(alpha = 0.9f),
                                fontFamily = FontFamily.Monospace,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // Detailed Color Values (HEX, RGB, CMYK, LAB) with copy actions
            item {
                ColorValuesSection(color = color)
            }

            // Combinations Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "COMBINATIONS (${combinations.size})",
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiary,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "From Dictionary",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }

            // List of combinations featuring this color
            if (combinations.isEmpty()) {
                item {
                    Text(
                        text = "No combinations found for this color.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            } else {
                items(combinations, key = { it.id }) { combination ->
                    CombinationCard(
                        combination = combination,
                        highlightColorId = color.id,
                        isFavorite = favoriteComboIds.contains(combination.id),
                        onFavoriteClick = { onToggleFavoriteCombo(combination.id) },
                        onColorClick = { companionColor ->
                            if (companionColor.id != color.id) {
                                onColorClick(companionColor.id)
                            }
                        },
                        onClick = { onCombinationClick(combination.id) }
                    )
                }
            }
        }
    }
}
