package com.gavinferiancek.ui_subjectlist.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.gavinferiancek.ui_subjectlist.ui.SubjectListState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun SubjectListHorizontalPager(
    state: SubjectListState,
    pagerState: PagerState,
    imageLoader: ImageLoader,
    navigateToDetailScreen: (Int) -> Unit,
) {
    HorizontalPager(
        count = state.subjects.size,
        state = pagerState,
    ) { page ->
        val spanCount = when (page) {
            0, 1 -> GridCells.Adaptive(108.dp)
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
                        onSelectSubject = navigateToDetailScreen,
                    )
                }
            }
        }
    }
}