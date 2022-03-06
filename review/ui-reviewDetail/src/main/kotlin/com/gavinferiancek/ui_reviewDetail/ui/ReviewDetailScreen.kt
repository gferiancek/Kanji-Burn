package com.gavinferiancek.ui_reviewDetail.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.gavinferiancek.components.BaseHorizontalTabRow
import com.gavinferiancek.components.BaseScreen
import com.gavinferiancek.core_domain.subject.Subject
import com.gavinferiancek.theme.kanji
import com.gavinferiancek.theme.radical
import com.gavinferiancek.theme.vocab
import com.gavinferiancek.ui_reviewDetail.components.ReviewDetailHeader
import com.gavinferiancek.ui_reviewDetail.components.SubjectDetailHorizontalPager
import com.gavinferiancek.ui_reviewDetail.components.SubjectDetailToolbar
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@ExperimentalFoundationApi
@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
fun ReviewDetailScreen(
    state: ReviewDetailState,
    imageLoader: ImageLoader,
    events: (ReviewDetailEvents) -> Unit,
    onNavigateUp: () -> Unit,
) {
    state.reviewSubject?.let { reviewSubject ->
        val tabData = mutableListOf<String>()
        val subjectColor = when (reviewSubject.subject) {
            is Subject.Radical -> {
                tabData.addAll(listOf("Name", "Found In Kanji", "Progress"))
                MaterialTheme.colors.radical
            }
            is Subject.Kanji -> {
                tabData.addAll(listOf("Meaning", "Reading", "Found In Vocabulary", "Progress"))
                MaterialTheme.colors.kanji
            }
            is Subject.Vocab -> {
                tabData.addAll(listOf("Meaning", "Reading", "Context", "Kanji Composition", "Progress"))
                MaterialTheme.colors.vocab
            }
        }
        BaseScreen(
            topBar = {
                SubjectDetailToolbar(
                    reviewSubject = reviewSubject,
                    srsStageName = state.srsStageName,
                    srsResourceId = state.srsResourceId,
                    color = subjectColor,
                    onNavigateUp = onNavigateUp,
                )
            },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                ReviewDetailHeader(
                    subject = reviewSubject.subject,
                    color = subjectColor,
                    imageLoader = imageLoader
                )
                val pagerState = rememberPagerState(initialPage = 0)
                BaseHorizontalTabRow(
                    pagerState = pagerState,
                    tabData = tabData,
                    tabColor = subjectColor,
                    scope = rememberCoroutineScope(),
                    isScrollable = true
                ) {
                    SubjectDetailHorizontalPager(
                        count = tabData.size,
                        pagerState = pagerState,
                        subject = reviewSubject.subject,
                        reviewStatistics = reviewSubject.reviewStatistics,
                        assignment = reviewSubject.assignment
                    )
                }
            }
        }
    }
}