package com.gavinferiancek.ui_reviewDetail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import com.gavinferiancek.core_domain.subject.ContextSentence
import com.gavinferiancek.core_ui.components.TitledCardView
import com.gavinferiancek.core_ui.theme.spacing

@Composable
fun ContextCard(
    title: String,
    contextSentences: List<ContextSentence>,
) {
    TitledCardView(title = title) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium)
        ) {
            Text(
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.small),
                text = "Context Sentences",
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onSurface,
            )
            contextSentences.forEach { contextSentence ->
                Text(
                    text = contextSentence.jp,
                    style = MaterialTheme.typography.body2
                )
                val isBlurred = remember { mutableStateOf(true) }
                Text(
                    modifier = Modifier
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