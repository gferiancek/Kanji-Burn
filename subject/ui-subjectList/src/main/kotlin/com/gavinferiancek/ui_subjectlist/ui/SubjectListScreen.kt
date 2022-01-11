package com.gavinferiancek.ui_subjectlist.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.gavinferiancek.components.BaseScreen
import com.gavinferiancek.kanjiburn.ui.theme.KanjiBurnTheme
import com.gavinferiancek.ui_subjectlist.components.EmptyListText
import com.gavinferiancek.ui_subjectlist.components.SubjectListItem
import com.gavinferiancek.ui_subjectlist.components.SubjectListToolBar
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun SubjectListScreen(
    state: SubjectListState,
    events: (SubjectListEvents) -> Unit,
    imageLoader: ImageLoader,
) {
    BaseScreen(
        progressBarState = state.progressBarState,
        topBar = {
            SubjectListToolBar(
                query = "",
                onQueryChanged = {}
            )
        }
    ) {
        val scope = rememberCoroutineScope()
        val pagerState = rememberPagerState(initialPage = 0)
        val tabData = listOf("Radical", "Kanji", "Vocabulary")
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier
                                .pagerTabIndicatorOffset(pagerState, tabPositions)
                        )
                    }
                ) {
                    tabData.forEachIndexed { index, title ->
                        Tab(
                            modifier = Modifier
                                .background(KanjiBurnTheme.colors.primary),
                            text = {
                                Text(
                                    text = title,
                                    fontSize = 18.sp,
                                    maxLines = 1,
                                )
                            },
                            selected = pagerState.currentPage == index,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(
                                        page = index,
                                    )
                                }
                            },
                        )
                    }
                }
            }
            HorizontalPager(
                count = state.subjects.size,
                state = pagerState,
            ) { page ->
                val spanCount = when (page) {
                    0, 1 -> GridCells.Adaptive(96.dp)
                    else -> GridCells.Fixed(1)
                }

                if (state.subjects[page].isEmpty()) EmptyListText()
                else {
                    LazyVerticalGrid(
                        cells = spanCount
                    ) {
                        items(state.subjects[page]) { subject ->
                            SubjectListItem(
                                subject = subject,
                                imageLoader = imageLoader,
                            )
                        }
                    }
                }
            }
        }
    }
}

