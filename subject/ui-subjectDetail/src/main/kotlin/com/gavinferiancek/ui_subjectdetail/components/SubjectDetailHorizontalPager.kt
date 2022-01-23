package com.gavinferiancek.ui_subjectdetail.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.gavinferiancek.subject_domain.Kanji
import com.gavinferiancek.subject_domain.Radical
import com.gavinferiancek.subject_domain.Subject
import com.gavinferiancek.subject_domain.Vocab
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
            is Radical -> {
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
            is Kanji -> {

            }
            is Vocab -> {

            }
        }
    }
}