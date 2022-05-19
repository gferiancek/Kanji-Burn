package com.gavinferiancek.core_ui.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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
            Text(text = title)
        },
        text = {
            if (description.isNotBlank()) Text(text = description)
        },
        buttons = {}
    )
}