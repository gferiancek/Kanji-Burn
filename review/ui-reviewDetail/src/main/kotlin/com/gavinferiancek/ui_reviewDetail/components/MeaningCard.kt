package com.gavinferiancek.ui_reviewDetail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import com.gavinferiancek.core_ui.components.TitledCardView
import com.gavinferiancek.core_ui.theme.spacing
import com.gavinferiancek.ui_reviewDetail.util.generateAnnotatedString

@Composable
fun MeaningCard(
    title: String,
    isVocab: Boolean = false,
    primaryMeaning: String,
    alternateMeanings: String,
    partsOfSpeech: String = "",
    userSynonyms: String,
    meaningText: String,
    meaningHint: String = "",
) {
    /**
     * Radicals are the building blocks on Kanji but they don't really have a meaning. Instead we assign them
     * a name that is used in the mnemonic to create a story that helps remember kanji/vocab that
     * contain that radical.  In order to not confuse learners we display name instead of meaning.
     */
    TitledCardView(
        title = title,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium),
        ) {
            val headerRows = mapOf(
                "PRIMARY" to primaryMeaning,
                "ALTERNATIVE" to alternateMeanings,
                "WORD TYPE" to partsOfSpeech,
                "USER SYNONYMS" to userSynonyms
            )

            headerRows.forEach { (header, text) ->
                when {
                    header == "USER SYNONYMS" -> HeaderTextRow(
                        header = header,
                        text = text
                    ) // Make UserSynonymRow with button to add synonym.
                    text.isNotBlank() -> HeaderTextRow(header = header, text = text)
                }
            }

            Text(
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.small),
                text = if (isVocab) "Explanation" else "Mnemonic",
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onSurface,
            )

            val uriHandler = LocalUriHandler.current
            val annotatedMeaning = generateAnnotatedString(sourceText = meaningText)
            ClickableText(
                text = annotatedMeaning,
                style = MaterialTheme.typography.caption.copy(
                    color = MaterialTheme.colors.onSurface,
                ),
                onClick = {
                    annotatedMeaning.getStringAnnotations(tag = "URL", start = it, end = it)
                        .firstOrNull()?.let { annotation ->
                            uriHandler.openUri(annotation.item)
                        }
                },
            )
            if (meaningHint.isNotBlank()) HintBox(meaningHint)
            Text(
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.small),
                text = "Note",
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onSurface,
            )
            Text(
                text = "Click to add note",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface,
            )
        }
    }
}
