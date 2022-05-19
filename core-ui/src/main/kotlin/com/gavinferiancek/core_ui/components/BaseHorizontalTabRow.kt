package com.gavinferiancek.core_ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.gavinferiancek.core_ui.theme.spacing
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
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
                    contentColor = MaterialTheme.colors.onPrimary,
                    edgePadding = MaterialTheme.spacing.medium
                ) {
                    tabData.forEachIndexed { index, title ->
                        Tab(
                            modifier = Modifier
                                .background(tabColor),
                            text = {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.h4,
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
                                Column {
                                    Text(text = title)
                                    AnimatedContent(targetState = 0,
                                        transitionSpec = {
                                            slideIntoContainer(towards = AnimatedContentScope.SlideDirection.Up).with(
                                                slideOutOfContainer(towards = AnimatedContentScope.SlideDirection.Down)
                                            )
                                        }
                                    ) { count ->
                                        Text(text = "($count)")
                                    }
                                }
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