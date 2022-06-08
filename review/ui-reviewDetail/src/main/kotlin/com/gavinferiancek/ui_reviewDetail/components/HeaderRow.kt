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
fun HeaderRow(
    header: String,
    text: String,
    headerTextColor: Color = Color.Gray,
    textColor: Color = MaterialTheme.colors.onSurface,
) {
    if (text.isNotBlank()) {
        Row {
            Text(
                modifier = Modifier
                    .padding(
                        end = MaterialTheme.spacing.small,
                    )
                    .alignByBaseline(),
                text = header,
                color = headerTextColor,
                style = MaterialTheme.typography.caption,
            )
            Text(
                modifier = Modifier
                    .alignByBaseline(),
                text = text,
                style = MaterialTheme.typography.body2,
                color = textColor,
            )
        }
    }
}