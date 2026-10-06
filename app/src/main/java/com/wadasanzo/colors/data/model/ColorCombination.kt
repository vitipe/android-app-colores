package com.wadasanzo.colors.data.model

import androidx.compose.runtime.Immutable

@Immutable
data class ColorCombination(
    val id: Int,
    val colors: List<SanzoColor>
) {
    val name: String = "Combination #$id"

    val typeName: String = when (colors.size) {
        2 -> "2-Color Combination"
        3 -> "3-Color Combination"
        4 -> "4-Color Combination"
        else -> "${colors.size}-Color Combination"
    }

    val colorNames: String by lazy { colors.joinToString(" + ") { it.name } }
}
