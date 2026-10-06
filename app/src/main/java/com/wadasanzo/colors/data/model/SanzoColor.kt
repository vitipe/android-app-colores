package com.wadasanzo.colors.data.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class SanzoColor(
    val id: Int,
    val name: String,
    val hex: String,
    val swatch: Int,
    val combinations: List<Int>,
    val cmyk: List<Int>,
    val rgb: List<Int>,
    val lab: List<Double>
) {
    // Pre-calculated once at initialization to eliminate allocations and math during scrolling
    val composeColor: Color = Color(
        rgb.getOrElse(0) { 0 },
        rgb.getOrElse(1) { 0 },
        rgb.getOrElse(2) { 0 }
    )

    val isDark: Boolean = run {
        val r = rgb.getOrElse(0) { 0 } / 255.0
        val g = rgb.getOrElse(1) { 0 } / 255.0
        val b = rgb.getOrElse(2) { 0 } / 255.0
        val luminance = 0.2126 * r + 0.7152 * g + 0.0722 * b
        luminance < 0.5
    }

    val textColor: Color = if (isDark) Color.White else Color(0xFF1E1E1E)

    val rgbFormatted: String = "RGB(${rgb.getOrElse(0){0}}, ${rgb.getOrElse(1){0}}, ${rgb.getOrElse(2){0}})"

    val cmykFormatted: String = "C:${cmyk.getOrElse(0){0}}% M:${cmyk.getOrElse(1){0}}% Y:${cmyk.getOrElse(2){0}}% K:${cmyk.getOrElse(3){0}}%"

    val labFormatted: String = if (lab.size >= 3) {
        "L:%.1f a:%.1f b:%.1f".format(lab[0], lab[1], lab[2])
    } else ""

    val swatchBookName: String = "Book ${swatch + 1}"
}
