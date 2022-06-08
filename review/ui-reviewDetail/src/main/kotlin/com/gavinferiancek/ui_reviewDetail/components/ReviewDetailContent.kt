package com.gavinferiancek.ui_reviewDetail.components

import android.media.MediaPlayer
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.input.TextFieldValue
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.gavinferiancek.core_domain.assignment.Assignment
import com.gavinferiancek.core_domain.reviewstatistics.ReviewStatistics
import com.gavinferiancek.core_domain.studymaterials.StudyMaterials
import com.gavinferiancek.core_domain.subject.*
import com.gavinferiancek.core_ui.theme.spacing
import com.gavinferiancek.review_domain.DetailEditState
import com.gavinferiancek.review_domain.DetailScreenItem
import com.gavinferiancek.review_domain.model.Connections
import com.gavinferiancek.ui_reviewDetail.util.generateAnnotatedString
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun ReviewDetailContent(
    screenItems: List<DetailScreenItem>,
    scope: CoroutineScope,
    lazyState: LazyListState,
    color: Color,
    imageLoader: ImageLoader,
    subject: Subject,
    reviewStatistics: ReviewStatistics,
    assignment: Assignment,
    studyMaterials: StudyMaterials,
    connections: Connections,
    mediaPlayer: MediaPlayer,
    editState: DetailEditState,
    textFieldValue: TextFieldValue,
    navigateToDetailScreen: (Int) -> Unit,
    updateStudyMaterials: (String) -> Unit,
    updateDetailEditState: (DetailEditState) -> Unit,
    updateTextFieldValue: (TextFieldValue) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val uriHandler = LocalUriHandler.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = lazyState
    ) {
        screenItems.forEach { screenItem ->
            item {
                when (screenItem) {
                    is DetailScreenItem.RadicalCompositionItem, is DetailScreenItem.KanjiCompositionItem -> {
                        ReviewDetialItem(title = screenItem.contentValue) {
                            ComponentsList(
                                components = connections.componentSubjects,
                                imageLoader = imageLoader,
                                navigateToDetailScreen = navigateToDetailScreen,
                            )
                        }
                    }
                    is DetailScreenItem.NameItem, is DetailScreenItem.MeaningItem -> {
                        ReviewDetialItem(title = screenItem.contentValue) {
                            DetailCard(
                                header = {
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
                                        focusRequester = focusRequester,
                                        keyboardController = keyboardController,
                                        scope = scope,
                                        lazyState = lazyState,
                                        isEditing = editState is DetailEditState.EditingUserSynonym,
                                        isDeleting = editState is DetailEditState.DeletingUserSynonym,
                                        updateStudyMaterials = updateStudyMaterials,
                                        updateDetailEditState = updateDetailEditState,
                                    )
                                },
                                body = {
                                    SubtitledText(subtitle = if (subject is Vocab) "Explanation" else "Mnemonic") {
                                        AnnotatedText(
                                            sourceText = subject.meaningMnemonic,
                                            uriHandler = uriHandler
                                        )
                                    }
                                    if (subject is Kanji) HintBox(hint = subject.meaningHint)
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
                                },
                            )
                        }
                    }
                    is DetailScreenItem.ReadingItem -> {
                        ReadingCard(
                            color = color,
                            title = screenItem.contentValue,
                            mediaPlayer = mediaPlayer,
                            studyMaterials = studyMaterials,
                            subject = subject,
                            editState = editState,
                            textFieldValue = textFieldValue,
                            focusRequester = focusRequester,
                            keyboardController = keyboardController,
                            updateStudyMaterials = updateStudyMaterials,
                            updateDetailEditState = updateDetailEditState,
                            updateTextFieldValue = updateTextFieldValue,
                        )
                    }
                    is DetailScreenItem.ContextItem -> {
                        if (subject is Vocab) {
                            ContextCard(
                                title = screenItem.contentValue,
                                contextSentences = subject.contextSentences
                            )
                        }
                    }
                    is DetailScreenItem.ProgressItem -> {
                        ProgressCard(
                            title = screenItem.contentValue,
                            isRadical = subject is Radical,
                            assignment = assignment,
                            reviewStatistics = reviewStatistics,
                            color = color
                        )
                    }
                    is DetailScreenItem.KanjiUsageItem, is DetailScreenItem.VocabUsageItem, is DetailScreenItem.SimilarKanjiItem -> {
                        val connectionsList =
                            if (screenItem is DetailScreenItem.SimilarKanjiItem) connections.visuallySimilarSubjects
                            else connections.amalgamationSubjects

                        ConnectionsList(
                            numberOfColumns =
                            if (connectionsList.isNotEmpty() && connectionsList.first() is Vocab) 1 else 4,
                            title = screenItem.contentValue,
                            connections = connectionsList,
                            imageLoader = imageLoader,
                            navigateToDetailScreen = navigateToDetailScreen,
                        )
                    }
                }
            }
        }
    }
}