package com.gavinferiancek.ui_reviewDetail.ui

import android.media.MediaPlayer
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.gavinferiancek.core_domain.UIComponent
import com.gavinferiancek.core_domain.state.UIComponentState
import com.gavinferiancek.ui_reviewDetail.components.ReviewDetailContent
import com.gavinferiancek.ui_reviewDetail.components.ReviewDetailHeader
import com.gavinferiancek.ui_reviewDetail.components.ReviewDetailToolbar
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
@OptIn(ExperimentalComposeUiApi::class)
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
                ReviewDetailContent(
                    screenItems = state.getScreenItems(),
                    scope = scope,
                    lazyState = lazyState,
                    color = subjectColor,
                    imageLoader = imageLoader,
                    subject = reviewSubject.subject,
                    reviewStatistics = reviewSubject.reviewStatistics,
                    assignment = reviewSubject.assignment,
                    studyMaterials = reviewSubject.studyMaterials,
                    connections = state.connections,
                    mediaPlayer = mediaPlayer,
                    editState = state.editState,
                    textFieldValue = state.textFieldValue,
                    navigateToDetailScreen = navigateToDetailScreen,
                    updateStudyMaterials = { updatedField ->
                        events(
                            ReviewDetailEvents.UpdateStudyMaterials(
                                updatedField = updatedField
                            )
                        )
                    },
                    updateDetailEditState = { editState ->
                        events(
                            ReviewDetailEvents.UpdateDetailEditState(
                                editState = editState
                            )
                        )
                    },
                    updateTextFieldValue = { textFieldValue ->
                        events(
                            ReviewDetailEvents.UpdateTextFieldValue(
                                textFieldValue = textFieldValue
                            )
                        )
                    },
                )
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
