package com.gavinferiancek.ui_reviewList.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.gavinferiancek.core_ui.components.BaseScreen
import com.gavinferiancek.core_ui.components.subject.SubjectList
import com.gavinferiancek.core_domain.state.UIComponentState
import com.gavinferiancek.core_ui.components.GenericDialog
import com.gavinferiancek.ui_reviewList.components.ReviewListTabRow
import com.gavinferiancek.ui_reviewList.components.SubjectListFilterDialog
import com.gavinferiancek.ui_reviewList.components.SubjectListToolBar
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun ReviewListScreen(
    state: ReviewListState,
    events: (ReviewListEvents) -> Unit,
    imageLoader: ImageLoader,
    navigateToDetailScreen: (Int) -> Unit,
) {
    BaseScreen(
        progressBarState = state.progressBarState,
        topBar = {
            SubjectListToolBar(
                query = state.query,
                onQueryChanged = { query ->
                    events(
                        ReviewListEvents.UpdateSearchQuery(
                            query = query
                        )
                    )
                },
                onExecuteSearch = { events(ReviewListEvents.FilterSubjects) },
                onShowFilterDialog = {
                    events(
                        ReviewListEvents.UpdateFilterDialogState(
                            uiComponentState = UIComponentState.Visible
                        )
                    )
                },
            )
        }
    ) {
        val pagerState = rememberPagerState(initialPage = 0)

        ReviewListTabRow(
            pagerState = pagerState,
            tabData = state.tabData,
            tabColor = MaterialTheme.colors.primaryVariant,
            initialListCount = state.initialListCount,
            filteredListCount = state.filteredListCount,
            hasEnteredQuery = state.query.isNotBlank(),
            scope = rememberCoroutineScope(),
        ) {
            HorizontalPager(
                count = state.tabData.size,
                state = pagerState,
            ) { page ->
                SubjectList(
                    subjects = state.filteredSubjects[page],
                    imageLoader = imageLoader,
                    navigateToDetailScreen = navigateToDetailScreen
                )
            }
        }

        if (state.filterDialogState is UIComponentState.Visible) {
            SubjectListFilterDialog(
                subjectFilter = state.subjectFilter,
                onUpdateSubjectFilter = { subjectFilter ->
                    events(
                        ReviewListEvents.UpdateSubjectFilter(
                            subjectFilter = subjectFilter
                        )
                    )
                },
                onCloseDialog = {
                    events(
                        ReviewListEvents.UpdateFilterDialogState(
                            uiComponentState = UIComponentState.Hidden
                        )
                    )
                },
            )
        }
    }
}