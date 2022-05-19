package com.gavinferiancek.ui_reviewDetail.ui

import android.media.MediaPlayer
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.gavinferiancek.core_ui.components.BaseScreen
import com.gavinferiancek.core_domain.subject.Subject
import com.gavinferiancek.core_ui.theme.kanji
import com.gavinferiancek.core_ui.theme.radical
import com.gavinferiancek.core_ui.theme.vocab
import com.gavinferiancek.ui_reviewDetail.components.ReviewDetailHeader
import com.gavinferiancek.ui_reviewDetail.components.ReviewDetailContent
import com.gavinferiancek.ui_reviewDetail.components.ReviewDetailToolbar
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
fun ReviewDetailScreen(
    state: ReviewDetailState,
    imageLoader: ImageLoader,
    mediaPlayer: MediaPlayer,
    events: (ReviewDetailEvents) -> Unit,
    onNavigateUp: () -> Unit,
    navigateToDetailScreen: (Int) -> Unit,
) {
    state.reviewSubject?.let { reviewSubject ->
        val subjectColor = when (reviewSubject.subject) {
            is Subject.Radical -> {
                MaterialTheme.colors.radical
            }
            is Subject.Kanji -> {
                MaterialTheme.colors.kanji
            }
            is Subject.Vocab -> {
                MaterialTheme.colors.vocab
            }
        }
        BaseScreen(
            topBar = {
                ReviewDetailToolbar(
                    subject = state.reviewSubject.subject,
                    srsStageData = state.srsStageData,
                    color = subjectColor,
                    onNavigateUp = onNavigateUp,
                )
            },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val lazyState = rememberLazyListState()
                ReviewDetailHeader(
                    subject = reviewSubject.subject,
                    color = subjectColor,
                    scope = rememberCoroutineScope(),
                    screenItems = state.getDetailScreenItems(),
                    lazyState = lazyState,
                    imageLoader = imageLoader,
                )
                ReviewDetailContent(
                    listState = lazyState,
                    screenItems = state.getDetailScreenItems(),
                    color = subjectColor,
                    imageLoader = imageLoader,
                    subject = reviewSubject.subject,
                    reviewStatistics = reviewSubject.reviewStatistics,
                    assignment = reviewSubject.assignment,
                    connections = state.connections,
                    mediaPlayer = mediaPlayer,
                    navigateToDetailScreen = navigateToDetailScreen
                )
            }
        }
    }
}
