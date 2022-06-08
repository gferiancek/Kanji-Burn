package com.gavinferiancek.core_ui.components

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GenericDialog(
    modifier: Modifier = Modifier,
    title: String,
    description: String = "",
    onRemoveHeadFromQueue: () -> Unit,
) {

    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onRemoveHeadFromQueue()
        },
        title = {
            LazyColumn(
                modifier = Modifier
                    .defaultMinSize(1.dp)
            ) {
                item {
                    Text(text = title)
                }
            }
        },
        text = {
            if (description.isNotBlank()) Text(text = description)
        },
        buttons = {}
    )
}