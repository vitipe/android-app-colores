package com.wadasanzo.colors.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wadasanzo.colors.data.model.SanzoColor

@Composable
fun ColorValuesSection(
    color: SanzoColor,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "COLOR VALUES",
            fontSize = 11.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.tertiary,
            letterSpacing = 1.sp
        )

        // HEX Row
        ColorValueRow(
            label = "HEX",
            value = color.hex.uppercase(),
            onCopy = { copyToClipboard(context, "HEX", color.hex.uppercase()) }
        )

        // RGB Row
        ColorValueRow(
            label = "RGB",
            value = "${color.rgb.getOrElse(0){0}}, ${color.rgb.getOrElse(1){0}}, ${color.rgb.getOrElse(2){0}}",
            onCopy = { copyToClipboard(context, "RGB", "rgb(${color.rgb.getOrElse(0){0}}, ${color.rgb.getOrElse(1){0}}, ${color.rgb.getOrElse(2){0}})") }
        )

        // CMYK Row
        ColorValueRow(
            label = "CMYK",
            value = "C:${color.cmyk.getOrElse(0){0}}%  M:${color.cmyk.getOrElse(1){0}}%  Y:${color.cmyk.getOrElse(2){0}}%  K:${color.cmyk.getOrElse(3){0}}%",
            onCopy = { copyToClipboard(context, "CMYK", color.cmykFormatted) }
        )

        // LAB Row
        if (color.lab.size >= 3) {
            ColorValueRow(
                label = "LAB",
                value = color.labFormatted,
                onCopy = { copyToClipboard(context, "LAB", color.labFormatted) }
            )
        }
    }
}

@Composable
private fun ColorValueRow(
    label: String,
    value: String,
    onCopy: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onCopy() }
            .border(
                1.dp,
                MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                RoundedCornerShape(8.dp)
            ),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = label,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.width(52.dp)
                )
                Text(
                    text = value,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Icon(
                imageVector = Icons.Outlined.ContentCopy,
                contentDescription = "Copy $label",
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

private fun copyToClipboard(context: Context, label: String, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipboard.setPrimaryClip(clip)
    Toast.makeText(context, "Copied $label ($text)", Toast.LENGTH_SHORT).show()
}
