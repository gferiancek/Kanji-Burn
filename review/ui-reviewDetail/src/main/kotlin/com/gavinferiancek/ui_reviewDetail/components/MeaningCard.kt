package com.gavinferiancek.ui_reviewDetail.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import com.gavinferiancek.core_domain.studymaterials.StudyMaterials
import com.gavinferiancek.core_ui.components.TitledCardView
import com.gavinferiancek.core_ui.theme.spacing
import com.gavinferiancek.review_domain.DetailEditState
import com.gavinferiancek.ui_reviewDetail.util.generateAnnotatedString
import kotlinx.coroutines.CoroutineScope

/**
 * Card Composable that displays relevant data about a Subject's Meaning.
 *
 * @param color Subject's color used as an accent color for UI items.
 * @param title Title text to be displayed over card
 * @param isVocab Used to determine what subtitle to show for meaningText. Following design on wanikani.com
 * Kanji/Radicals have a Meaning Mnemonic and Vocab have a Meaning Explanation.
 * @param studyMaterials Contains user generated data about the meaning. (User Synonyms and notes)
 * @param primaryMeaning Primary meaning of the subject
 * @param alternateMeanings String consisting of all alternate/non primary meanings.
 * @param partsOfSpeech Vocabulary field that describes the word type. (Noun, Verb, etc.)
 * @param meaningText Main text/mnenomic for the Subject's meaning
 * @param meaningHint Kanji field that provides additional data/tips about the meaning.
 */
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun MeaningCard(
    color: Color,
    title: String,
    isVocab: Boolean,
    studyMaterials: StudyMaterials,
    primaryMeaning: String,
    alternateMeanings: String,
    partsOfSpeech: String,
    meaningText: String,
    meaningHint: String,
    scope: CoroutineScope,
    listState: LazyListState,
    editState: DetailEditState,
    textFieldValue: TextFieldValue,
    focusRequester: FocusRequester,
    keyboardController: SoftwareKeyboardController?,
    updateStudyMaterials: (String) -> Unit,
    updateDetailEditState: (DetailEditState) -> Unit,
    updateTextFieldValue: (TextFieldValue) -> Unit,
) {
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
            )
            headerRows.forEach { (header, text) ->
                if (text.isNotBlank()) {
                        Row {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        end = MaterialTheme.spacing.small,
                                    )
                                    .alignByBaseline(),
                                text = header,
                                color = Color.Gray,
                                style = MaterialTheme.typography.caption,
                            )
                            Text(
                                modifier = Modifier
                                    .alignByBaseline(),
                                text = text,
                                style = MaterialTheme.typography.body2,
                                color = MaterialTheme.colors.onSurface,
                            )
                        }
                    }
                }

            UserSynonymsRow(
                meaningSynonyms = studyMaterials.meaningSynonyms,
                color = color,
                focusRequester = focusRequester,
                keyboardController = keyboardController,
                scope = scope,
                lazyState = listState,
                isEditing = editState is DetailEditState.EditingUserSynonym,
                isDeleting = editState is DetailEditState.DeletingUserSynonym,
                updateStudyMaterials = updateStudyMaterials,
                updateDetailEditState = updateDetailEditState,
            )

            Text(
                text = if (isVocab) "Explanation" else "Mnemonic",
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onSurface,
            )

            val uriHandler = LocalUriHandler.current
            val annotatedMeaning = generateAnnotatedString(sourceText = meaningText)
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

            if (meaningHint.isNotBlank()) HintBox(meaningHint)

            UserNote(
                userNote = studyMaterials.meaningNote,
                textFieldValue = textFieldValue,
                color = color,
                isEditing = editState is DetailEditState.EditingMeaning,
                targetEditState = DetailEditState.EditingMeaning,
                focusRequester = focusRequester,
                keyboardController = keyboardController,
                updateDetailEditState = updateDetailEditState,
                updateStudyMaterials = updateStudyMaterials,
                updateTextFieldValue = updateTextFieldValue,
            )
        }
    }
}

