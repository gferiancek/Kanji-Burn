package com.gavinferiancek.core_ui.components.text

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import com.gavinferiancek.core_ui.util.generateAnnotatedString

/**
 * Composable that is used to display annotated strings.
 * @param sourceText The original text, with markup tags present, that will be annotated and then
 * displayed on screen.
 */
@Composable
fun AnnotatedText(
    sourceText: String,
) {
    val annotatedMeaning = generateAnnotatedString(sourceText = sourceText)
    val uriHandler = LocalUriHandler.current
    ClickableText(
        text = annotatedMeaning,
        style = MaterialTheme.typography.body2.copy(
            color = MaterialTheme.colors.onSurface,
        ),
        onClick = { offset ->
            annotatedMeaning.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    uriHandler.openUri(annotation.item)
                }
        },
    )
}