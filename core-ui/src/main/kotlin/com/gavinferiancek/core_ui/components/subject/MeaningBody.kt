package com.gavinferiancek.core_ui.components.subject

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import com.gavinferiancek.core_domain.state.StudyMaterialsEditState
import com.gavinferiancek.core_domain.studymaterials.StudyMaterials
import com.gavinferiancek.core_domain.subject.*
import com.gavinferiancek.core_ui.components.text.AnnotatedText
import com.gavinferiancek.core_ui.components.text.SubtitledText
import kotlinx.coroutines.CoroutineScope

/**
 * Composable that displays relevant data about a Subject's Meaning.
 *
 * @param color Subject's color used as an accent color for UI items.
 * @param title Title text to be displayed over card
 * @param subject Subject for the given page. Opted to pass in the whole subject instead of required fields
 * as it severely cuts down on parameters and the subject shouldn't change so no unnecessary recompositions will
 * be called.
 * @param studyMaterials Contains user generated data about the meaning. (User Synonyms and notes)
 * @param scope Coroutine Scope needed for list scrolling
 * @param lazyState LazyListState for ReviewDetailContent allowing us to scroll in UserSynonymsRow
 * @param textFieldValue TextFieldValue is required so that we can edit the cursor position in the event that
 * a Meaning/Reading note already exists. (We move the cursor to the end of the text.) Stored in State.
 * @param updateStudyMaterials Event to Post/Put updated StudyMaterials to Wanikani Server.
 * @param updateDetailEditState Event to update states ReviewDetailEditState.
 * @param updateTextFieldValue Event to update states TextFieldValue.
 */
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun MeaningBody(
    color: Color,
    subject: Subject,
    studyMaterials: StudyMaterials,
    scope: CoroutineScope,
    lazyState: LazyListState,
    editState: StudyMaterialsEditState,
    textFieldValue: TextFieldValue,
    updateStudyMaterials: (String) -> Unit,
    updateDetailEditState: (StudyMaterialsEditState) -> Unit,
    updateTextFieldValue: (TextFieldValue) -> Unit,
) {
    Column {
        HeaderRow(
            header = "PRIMARY",
            text = subject.meanings.getPrimaryMeaning()
        )
        HeaderRow(
            header = "ALTERNATIVE",
            text = subject.meanings.getAlternateMeanings()
        )
        if (subject is Vocab) {
            HeaderRow(
                header = "WORD TYPE",
                text = subject.buildPartsOfSpeechString()
            )
        }
        UserSynonymsRow(
            meaningSynonyms = studyMaterials.meaningSynonyms,
            color = color,
            scope = scope,
            lazyState = lazyState,
            isEditing = editState is StudyMaterialsEditState.EditingUserSynonym,
            isDeleting = editState is StudyMaterialsEditState.DeletingUserSynonym,
            updateStudyMaterials = updateStudyMaterials,
            updateDetailEditState = updateDetailEditState,
        )
        SubtitledText(subtitle = if (subject is Vocab) "Explanation" else "Mnemonic") {
            AnnotatedText(
                sourceText = subject.meaningMnemonic,
            )
        }
        if (subject is Kanji) HintBox(hint = subject.meaningHint)
        UserNote(
            userNote = studyMaterials.meaningNote,
            textFieldValue = textFieldValue,
            color = color,
            isEditing = editState is StudyMaterialsEditState.EditingMeaningNote,
            targetEditState = StudyMaterialsEditState.EditingMeaningNote,
            updateDetailEditState = updateDetailEditState,
            updateStudyMaterials = updateStudyMaterials,
            updateTextFieldValue = updateTextFieldValue,
        )
    }
}

