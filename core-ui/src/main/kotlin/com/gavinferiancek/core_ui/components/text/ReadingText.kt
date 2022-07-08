package com.gavinferiancek.core_ui.components.text

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow

/**
 * It seems overkill to extract out such a simple composable, but this same style is used everywhere
 * we display a subject's Reading text. Extracted out to avoid typing the same styles in multiple places,
 * but will reconsider later on into the project whether it is actually needed.
 */
@Composable
fun ReadingText(
    reading: String,
    style: TextStyle = MaterialTheme.typography.h4,
) {
    Text(
        text = reading,
        color = MaterialTheme.colors.onPrimary,
        style = style,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}