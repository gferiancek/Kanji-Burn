package com.gavinferiancek.ui_subjectdetail.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.gavinferiancek.subject_domain.Subject
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