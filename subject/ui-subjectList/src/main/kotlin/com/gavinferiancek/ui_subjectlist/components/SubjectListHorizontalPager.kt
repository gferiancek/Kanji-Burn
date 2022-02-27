package com.gavinferiancek.ui_subjectlist.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        count = state.filteredSubjects.size,
        state = pagerState,
    ) { page ->
        val spanCount = when (page) {
            0, 1 -> GridCells.Fixed(4) // 108.dp for 3 items
            else -> GridCells.Fixed(1)
        }

        if (state.filteredSubjects[page].isEmpty()) EmptyListText()
        else {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize(),
                cells = spanCount,
                verticalArrangement = Arrangement.Top
            ) {
                items(state.filteredSubjects[page]) { subject ->
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