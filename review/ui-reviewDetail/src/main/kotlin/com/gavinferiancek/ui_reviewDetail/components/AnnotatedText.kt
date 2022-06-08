package com.gavinferiancek.ui_reviewDetail.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.UriHandler
import com.gavinferiancek.ui_reviewDetail.util.generateAnnotatedString

@Composable
fun AnnotatedText(
    sourceText: String,
    uriHandler: UriHandler? = null,
) {
    val annotatedMeaning = generateAnnotatedString(sourceText = sourceText)
    ClickableText(
        text = annotatedMeaning,
        style = MaterialTheme.typography.body2.copy(
            color = MaterialTheme.colors.onSurface,
        ),
        onClick = { offset ->
            annotatedMeaning.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    uriHandler?.openUri(annotation.item)
                }
        },
    )
}