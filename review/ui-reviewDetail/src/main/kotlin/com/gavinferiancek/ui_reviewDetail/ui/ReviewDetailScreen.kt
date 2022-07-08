package com.gavinferiancek.ui_reviewDetail.ui

import android.media.MediaPlayer
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.gavinferiancek.core_domain.UIComponent
import com.gavinferiancek.core_domain.state.UIComponentState
import com.gavinferiancek.core_domain.subject.Radical
import com.gavinferiancek.core_domain.subject.Vocab
import com.gavinferiancek.core_ui.components.subject.ComponentsList
import com.gavinferiancek.core_ui.components.subject.NestedSubjectList
import com.gavinferiancek.core_domain.state.StudyMaterialsEditState
import com.gavinferiancek.core_ui.components.KanjiBurnCard
import com.gavinferiancek.core_ui.components.subject.MeaningBody
import com.gavinferiancek.core_ui.components.subject.ReadingBody
import com.gavinferiancek.review_domain.DetailScreenItem
import com.gavinferiancek.ui_reviewDetail.components.*
import com.google.accompanist.pager.ExperimentalPagerApi

/**
 * Main Composable for the ReviewDetailScreen.
 *
 * @param state ReviewDetailState passed in from the ReviewDetailViewModel
 * @param imageLoader Apps Coil ImageLoader object
 * @param mediaPlayer Apps MediaPlayer object
 * @param events ReviewDetailEvents used to interact with the ViewModel
 * @param onNavigateUp Used in ReviewDetailToolbar to navigate up the backstack
 * @param navigateToDetailScreen Navigate to Detail page of a clicked SubjectTile
 */
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
fun ReviewDetailScreen(
    state: ReviewDetailState,
    imageLoader: ImageLoader,
    mediaPlayer: MediaPlayer,
    events: (ReviewDetailEvents) -> Unit,
    onNavigateUp: () -> Unit,
    navigateToDetailScreen: (Int) -> Unit,
) {
    state.reviewSubject?.let { reviewSubject ->
        val scaffoldState = rememberScaffoldState()
        val subjectColor = state.getSubjectColor()

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                ReviewDetailToolbar(
                    subject = state.reviewSubject.subject,
                    srsStageData = state.getSrsStageData(),
                    color = subjectColor,
                    onNavigateUp = onNavigateUp,
                )
            },
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                val lazyState = rememberLazyListState()
                val scope = rememberCoroutineScope()

                ReviewDetailHeader(
                    subject = reviewSubject.subject,
                    color = subjectColor,
                    scope = scope,
                    screenItems = state.getScreenItems(),
                    lazyState = lazyState,
                    imageLoader = imageLoader,
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    state = lazyState
                ) {
                    val updateStudyMaterials = { updatedField: String ->
                        events(ReviewDetailEvents.UpdateStudyMaterials(updatedField = updatedField))
                    }
                    val updateDetailEditState = { editState: StudyMaterialsEditState ->
                        events(ReviewDetailEvents.UpdateDetailEditState(editState = editState))
                    }
                    val updateTextFieldValue = { textFieldValue: TextFieldValue ->
                        events(ReviewDetailEvents.UpdateTextFieldValue(textFieldValue = textFieldValue))
                    }
                    state.getScreenItems().forEach { screenItem ->
                        item {
                            ReviewDetailItem(title = screenItem.contentValue) {
                                when (screenItem) {
                                    is DetailScreenItem.RadicalCompositionItem, is DetailScreenItem.KanjiCompositionItem -> {
                                        ComponentsList(
                                            components = state.connections.componentSubjects,
                                            imageLoader = imageLoader,
                                            navigateToDetailScreen = navigateToDetailScreen,
                                        )
                                    }
                                    is DetailScreenItem.NameItem, is DetailScreenItem.MeaningItem -> {
                                        KanjiBurnCard {
                                            MeaningBody(
                                                color = subjectColor,
                                                subject = reviewSubject.subject,
                                                studyMaterials = reviewSubject.studyMaterials,
                                                scope = scope,
                                                lazyState = lazyState,
                                                editState = state.editState,
                                                textFieldValue = state.textFieldValue,
                                                updateStudyMaterials = updateStudyMaterials,
                                                updateDetailEditState = updateDetailEditState,
                                                updateTextFieldValue = updateTextFieldValue,
                                            )
                                        }
                                    }
                                    is DetailScreenItem.ReadingItem -> {
                                        KanjiBurnCard {
                                            ReadingBody(
                                                color = subjectColor,
                                                mediaPlayer = mediaPlayer,
                                                studyMaterials = reviewSubject.studyMaterials,
                                                subject = reviewSubject.subject,
                                                textFieldValue = state.textFieldValue,
                                                editState = state.editState,
                                                updateStudyMaterials = updateStudyMaterials,
                                                updateDetailEditState = updateDetailEditState,
                                                updateTextFieldValue = updateTextFieldValue,
                                            )
                                        }
                                    }
                                    is DetailScreenItem.ContextItem -> {
                                        // Vocab is the only Subject that will have a ContextCard so it
                                        // is safe to cast it to vocab to access the contextSentences.
                                        KanjiBurnCard {
                                            ContextBody(
                                                contextSentences = (reviewSubject.subject as Vocab).contextSentences
                                            )
                                        }
                                    }
                                    is DetailScreenItem.ProgressItem -> {
                                        ProgressCard(
                                            isRadical = reviewSubject.subject is Radical,
                                            assignment = reviewSubject.assignment,
                                            reviewStatistics = reviewSubject.reviewStatistics,
                                            color = subjectColor,
                                        )
                                    }
                                    is DetailScreenItem.KanjiUsageItem, is DetailScreenItem.VocabUsageItem, is DetailScreenItem.SimilarKanjiItem -> {
                                        val connectionsList =
                                            if (screenItem is DetailScreenItem.SimilarKanjiItem) state.connections.visuallySimilarSubjects
                                            else state.connections.amalgamationSubjects

                                        NestedSubjectList(
                                            numberOfColumns =
                                            if (connectionsList.isNotEmpty() && connectionsList.first() is Vocab) 1 else 4,
                                            title = screenItem.contentValue,
                                            subjects = connectionsList,
                                            imageLoader = imageLoader,
                                            navigateToDetailScreen = navigateToDetailScreen,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                if (state.snackbarState is UIComponentState.Visible) {
                    val context = LocalContext.current
                    LaunchedEffect(scaffoldState.snackbarHostState) {
                        val snackbarData = state.snackbarComponent as UIComponent.SnackBar
                        val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                            message = context.getString(snackbarData.messageResId),
                            actionLabel = snackbarData.action,
                        )
                        when (snackBarResult) {
                            SnackbarResult.ActionPerformed -> {
                                events(ReviewDetailEvents.UpdateSnackbarState(uiComponentState = UIComponentState.Hidden))
                            }
                            SnackbarResult.Dismissed -> {
                                events(ReviewDetailEvents.UpdateSnackbarState(uiComponentState = UIComponentState.Hidden))
                            }
                        }
                    }
                }
            }
        }
    }
}
