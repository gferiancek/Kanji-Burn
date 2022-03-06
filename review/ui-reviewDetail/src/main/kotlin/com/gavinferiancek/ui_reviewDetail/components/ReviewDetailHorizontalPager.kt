package com.gavinferiancek.ui_reviewDetail.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.gavinferiancek.core_domain.assignment.Assignment
import com.gavinferiancek.core_domain.reviewstatistics.ReviewStatistics
import com.gavinferiancek.core_domain.subject.Subject
import com.gavinferiancek.review_domain.model.ReviewSubject
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState

@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun SubjectDetailHorizontalPager(
    count: Int,
    pagerState: PagerState,
    subject: Subject,
    reviewStatistics: ReviewStatistics,
    assignment: Assignment,
) {
    HorizontalPager(
        count = count,
        state = pagerState
    ) { page ->
        when (subject) {
            is Subject.Radical -> {
                when (page) {
                    0 -> {
                        NamePagerItem(
                            primaryMeaning = subject.getPrimaryMeaning(),
                            userSynonyms = "",
                            meaningMnemonic = subject.meaningMnemonic,
                        )
                    }
                    1 -> {
                        Text(
                            "GridList - In Progress"
                        )
                    }
                    2 -> {
                        Text(
                            "Progress - In Progress"
                        )
                    }
                }
            }
            is Subject.Kanji -> {

            }
            is Subject.Vocab -> {

            }
        }
    }
}