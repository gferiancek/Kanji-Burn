package com.gavinferiancek.ui_reviewDetail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gavinferiancek.core_ui.theme.spacing

@Composable
fun DetailCard(
    header: @Composable () -> Unit,
    body: @Composable () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = MaterialTheme.spacing.small,
                end = MaterialTheme.spacing.small,
            ),
        backgroundColor = MaterialTheme.colors.surface,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium),
        ) {
            header()
            Spacer(modifier = Modifier.padding(top = MaterialTheme.spacing.small))
            body()
        }
    }
}