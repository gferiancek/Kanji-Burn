package com.gavinferiancek.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gavinferiancek.kanjiburn.ui.theme.KanjiBurnTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun BaseHorizontalTabRow(
    pagerState: PagerState,
    tabData: List<String>,
    tabColor: Color,
    scope: CoroutineScope,
    isScrollable: Boolean = false,
    horizontalPager: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (isScrollable) {
                ScrollableTabRow(
                    selectedTabIndex = pagerState.currentPage,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier
                                .pagerTabIndicatorOffset(pagerState, tabPositions)
                        )
                    },
                    backgroundColor = tabColor,
                    contentColor = KanjiBurnTheme.colors.onPrimary,
                    edgePadding = 16.dp
                ) {
                    tabData.forEachIndexed { index, title ->
                        Tab(
                            modifier = Modifier
                                .background(tabColor),
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
            } else {
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
                                .background(tabColor),
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
        }
        horizontalPager()
    }
}