package com.gavinferiancek.ui_reviewDetail.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.gavinferiancek.core_ui.theme.spacing

@Composable
fun HeaderTextRow(
    header: String,
    text: String,
) {
    Row() {
        Text(
            modifier = Modifier
                .padding(
                    end = MaterialTheme.spacing.small,
                )
                .alignByBaseline(),
            text = header,
            color = Color.Gray,
            style = MaterialTheme.typography.caption,
        )
        Text(
            modifier = Modifier
                .alignByBaseline(),
            text = text,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onSurface,
        )
    }
}