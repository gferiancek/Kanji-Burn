package com.gavinferiancek.ui_reviewDetail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

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