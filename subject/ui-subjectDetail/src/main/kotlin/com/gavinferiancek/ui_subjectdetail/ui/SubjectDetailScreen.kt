package com.gavinferiancek.ui_subjectdetail.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.gavinferiancek.components.BaseHorizontalTabRow
import com.gavinferiancek.components.BaseScreen
import com.gavinferiancek.kanjiburn.ui.theme.KanjiBurnTheme
import com.gavinferiancek.subject_domain.Kanji
import com.gavinferiancek.subject_domain.Radical
import com.gavinferiancek.subject_domain.Vocab
import com.gavinferiancek.ui_subjectdetail.R
import com.gavinferiancek.ui_subjectdetail.components.SubjectDetailHeader
import com.gavinferiancek.ui_subjectdetail.components.SubjectDetailHorizontalPager
import com.gavinferiancek.ui_subjectdetail.components.SubjectDetailToolbar
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope

@ExperimentalFoundationApi
@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
fun SubjectDetailScreen(
    state: SubjectDetailState,
    imageLoader: ImageLoader,
    events: (SubjectDetailEvents) -> Unit,
    onNavigateUp: () -> Unit,
) {
    val backgroundColor = when (state.subject) {
        is Radical -> KanjiBurnTheme.colors.radical
        is Kanji -> KanjiBurnTheme.colors.kanji
        is Vocab -> KanjiBurnTheme.colors.vocab
        else -> KanjiBurnTheme.colors.radical
    }
    BaseScreen(topBar = {
        SubjectDetailToolbar(
            state = state,
            color = backgroundColor,
            onNavigateUp = onNavigateUp,
        )
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            state.subject?.let {
                SubjectDetailHeader(
                    state = state,
                    imageLoader = imageLoader
                )

                val pagerState = rememberPagerState(initialPage = 0)
                var tabData: List<String> = listOf()
                var tabColor: Color = KanjiBurnTheme.colors.primary
                when (state.subject) {
                    is Radical -> {
                        tabData = listOf("Name", "Found In Kanji", "Progress")
                        tabColor = KanjiBurnTheme.colors.radical
                    }
                    is Kanji -> {
                        tabData = listOf("Meaning", "Reading", "Found In Vocabulary", "Progress")
                        tabColor = KanjiBurnTheme.colors.kanji
                    }
                    is Vocab -> {
                        tabData =
                            listOf("Meaning", "Reading", "Context", "Kanji Composition", "Progress")
                        tabColor = KanjiBurnTheme.colors.vocab
                    }
                }
                BaseHorizontalTabRow(pagerState = pagerState, tabData = tabData, tabColor = tabColor, scope = rememberCoroutineScope(), isScrollable = true) {
                    SubjectDetailHorizontalPager(
                        count = tabData.size,
                        pagerState = pagerState,
                        subject = state.subject
                    )
                }
            }
        }
    }
}