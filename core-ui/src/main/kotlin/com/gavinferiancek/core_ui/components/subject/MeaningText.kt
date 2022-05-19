package com.gavinferiancek.core_ui.components.subject

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow

/**
 * It seems overkill to extract out such a simple composable, but this same style is used everywhere
 * we display a subject's Meaning text. Extracted out to avoid typing the same styles in multiple places,
 * but will reconsider later on into the project whether it is actually needed.
 */
@Composable
fun MeaningText(
    meaning: String,
    style: TextStyle = MaterialTheme.typography.caption,
) {
    Text(
        text = meaning,
        color = MaterialTheme.colors.onPrimary,
        style = style,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}