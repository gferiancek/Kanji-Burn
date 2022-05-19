package com.gavinferiancek.core_ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.gavinferiancek.core_ui.theme.spacing

@Composable
fun TitledCardView(
    title: String,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = MaterialTheme.spacing.small,
                bottom = MaterialTheme.spacing.small,
            ),
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = title,
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center,
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = MaterialTheme.spacing.small,
                    end = MaterialTheme.spacing.small,
                ),
            backgroundColor = MaterialTheme.colors.surface,
        ) {
            content()
        }
    }
}