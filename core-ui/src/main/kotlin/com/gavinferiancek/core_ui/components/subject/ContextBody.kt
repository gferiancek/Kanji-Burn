package com.gavinferiancek.ui_reviewDetail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gavinferiancek.core_domain.subject.ContextSentence
import com.gavinferiancek.core_ui.R
import com.gavinferiancek.core_ui.components.text.SubtitledText
import com.gavinferiancek.core_ui.theme.spacing

/**
 * Composable displaying relevant Context data for Vocabulary Subjects.
 * TODO Modifier.blur() is Android 12+; Need to find a workaround for older devices.
 *
 * @param contextSentences List of [ContextSentence][com.gavinferiancek.core_domain.subject.ContextSentence] objects.
 * Contains both JP and EN context sentences for the subject.
 */
@Composable
fun ContextBody(
    contextSentences: List<ContextSentence>,
) {
    Column {
        SubtitledText(subtitle = stringResource(id = R.string.contextSentences_subtitle)) {
            contextSentences.forEach { contextSentence ->
                Text(
                    text = contextSentence.jp,
                    style = MaterialTheme.typography.body2
                )
                val isBlurred = remember { mutableStateOf(true) }
                Text(
                    modifier = Modifier
                        .padding(bottom = MaterialTheme.spacing.small)
                        .blur(if (isBlurred.value) 5.dp else 0.dp)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null,
                            enabled = true,
                            onClick = {
                                isBlurred.value = !isBlurred.value
                            },
                        ),
                    text = contextSentence.en,
                    style = MaterialTheme.typography.caption,
                )
            }
        }
    }
}