package com.gavinferiancek.core_ui.components.text

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

/**
 * Composable used to display Subtitle over a body of text.
 */
@Composable
fun SubtitledText(
    subtitle: String,
    text: @Composable () -> Unit,
) {
    Column {
        Text(
            text = subtitle,
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.onSurface,
        )
        text()
    }
}