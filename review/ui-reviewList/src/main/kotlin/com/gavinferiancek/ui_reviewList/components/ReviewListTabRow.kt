package com.gavinferiancek.ui_reviewList.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun ReviewListTabRow(
    pagerState: PagerState,
    tabData: List<String>,
    tabColor: Color,
    initialListCount: List<Int>,
    filteredListCount: List<Int>,
    hasEnteredQuery: Boolean,
    scope: CoroutineScope,
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
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = title)
                                AnimatedContent(
                                    targetState = filteredListCount[index] < initialListCount[index],
                                    transitionSpec = {
                                        slideIntoContainer(towards = AnimatedContentScope.SlideDirection.Up).with(
                                            slideOutOfContainer(towards = AnimatedContentScope.SlideDirection.Down)
                                        )
                                    },
                                    contentAlignment = Alignment.Center,
                                ) { showListCount ->
                                    /**
                                     * The goal is to only show the # of items in the list if the user has entered a search query,
                                     * meaning we never want to display text if the filteredListCount[index] == initialListCount[index].
                                     * If we use Text(text = filteredListCount[index]) then either the ENTER or the EXIT animation will be wrong.
                                     *
                                     * @Scenario 1: targetState = hasEnteredQuery - Animations are played BEFORE subjects are
                                     * filtered, so ENTER shows the wrong value and EXIT shows the correct value. (ENTER animation
                                     * runs, showing the initialListCount and then updates it to filteredListCount.)
                                     *
                                     * @Scenario 2: targetState = filteredListCount[index] < initialListCount[index] - Animations are played
                                     * AFTER subjects are filtered, so ENTER displays the correct value, and EXIT displays the
                                     * wrong value. (Text is updated to show the initialListCount[index] and then it is animated off screen)
                                     *
                                     * To fix this, we use Scenario 2 to get the correct ENTER animation, but store filteredListCount[index]
                                     * inside of cachedListCount. If hasEnteredQuery is false then we do not update cachedListCount
                                     * which shows the correct EXIT animation. (Text will not be updated and will stay "stuck" on the
                                     * last known filteredListCount[index] and is animated off screen in that state.)
                                     */
                                    var cachedListCount by remember { mutableStateOf(0) }
                                    if (hasEnteredQuery) cachedListCount = filteredListCount[index]
                                    if (showListCount) Text(text = "(${cachedListCount})")
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
        horizontalPager()
    }
}