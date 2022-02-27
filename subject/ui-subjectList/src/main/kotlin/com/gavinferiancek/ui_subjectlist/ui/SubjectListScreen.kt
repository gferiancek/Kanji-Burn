package com.gavinferiancek.ui_subjectlist.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.gavinferiancek.components.BaseHorizontalTabRow
import com.gavinferiancek.components.BaseScreen
import com.gavinferiancek.core.domain.UIComponentState
import com.gavinferiancek.kanjiburn.ui.theme.KanjiBurnTheme
import com.gavinferiancek.ui_subjectlist.components.SubjectListFilterDialog
import com.gavinferiancek.ui_subjectlist.components.SubjectListHorizontalPager
import com.gavinferiancek.ui_subjectlist.components.SubjectListToolBar
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@ExperimentalComposeUiApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun SubjectListScreen(
    state: SubjectListState,
    events: (SubjectListEvents) -> Unit,
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
                        SubjectListEvents.UpdateSearchQuery(
                            query = query
                        )
                    )
                },
                onExecuteSearch = { events(SubjectListEvents.FilterSubjects) },
                onShowFilterDialog = {
                    events(
                        SubjectListEvents.UpdateFilterDialogState(
                            uiComponentState = UIComponentState.Visible
                        )
                    )
                },
            )
        }
    ) {
        val pagerState = rememberPagerState(initialPage = 0)
        val tabData = listOf("Radical", "Kanji", "Vocabulary")

        BaseHorizontalTabRow(
            pagerState = pagerState,
            tabData = tabData,
            tabColor = KanjiBurnTheme.colors.primary,
            scope = rememberCoroutineScope(),
        ) {
            SubjectListHorizontalPager(
                state = state,
                pagerState = pagerState,
                imageLoader = imageLoader,
                navigateToDetailScreen = navigateToDetailScreen,
            )
        }

        if (state.filterDialogState is UIComponentState.Visible) {
            SubjectListFilterDialog(
                subjectFilter = state.subjectFilter,
                onUpdateSubjectFilter = { subjectFilter ->
                    events(
                        SubjectListEvents.UpdateSubjectFilter(
                            subjectFilter = subjectFilter
                        )
                    )
                },
                onCloseDialog = {
                    events(
                        SubjectListEvents.UpdateFilterDialogState(
                            uiComponentState = UIComponentState.Hidden
                        )
                    )
                },
            )
        }
    }
}

