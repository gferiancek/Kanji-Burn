package com.gavinferiancek.ui_reviewDetail.components

import android.media.MediaPlayer
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.gavinferiancek.core_domain.assignment.Assignment
import com.gavinferiancek.core_domain.reviewstatistics.ReviewStatistics
import com.gavinferiancek.core_domain.subject.Subject
import com.gavinferiancek.review_domain.DetailScreenItem
import com.gavinferiancek.review_domain.model.Connections
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun ReviewDetailContent(
    screenItems: List<DetailScreenItem>,
    listState: LazyListState,
    color: Color,
    imageLoader: ImageLoader,
    subject: Subject,
    reviewStatistics: ReviewStatistics,
    assignment: Assignment,
    connections: Connections?,
    mediaPlayer: MediaPlayer,
    navigateToDetailScreen: (Int) -> Unit,
) {
    // Connections should never be null once the page has fully loaded, so we can avoid multiple
    // checks by just not doing any composition until connections isn't null.
    connections?.let {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = listState
        ) {
            screenItems.forEach { screenItem ->
                item {
                    when (screenItem) {
                        is DetailScreenItem.RadicalCompositionItem, is DetailScreenItem.KanjiCompositionItem -> {
                            ComponentsList(
                                title = screenItem.contentValue,
                                components = connections.componentSubjects,
                                imageLoader = imageLoader,
                                navigateToDetailScreen = navigateToDetailScreen,
                            )
                        }
                        is DetailScreenItem.NameItem, is DetailScreenItem.MeaningItem -> {
                            MeaningCard(
                                title = screenItem.contentValue,
                                isVocab = subject is Subject.Vocab,
                                primaryMeaning = subject.getPrimaryMeaning(),
                                alternateMeanings = subject.getAlternateMeanings(),
                                userSynonyms = "",
                                meaningText = subject.meaningMnemonic,
                                meaningHint = if (subject is Subject.Kanji) subject.meaningHint else "",
                                partsOfSpeech = if (subject is Subject.Vocab) subject.buildPartsOfSpeechString() else "",
                            )
                        }
                        is DetailScreenItem.ReadingItem -> {
                            if (subject is Subject.Kanji) {
                                ReadingCard(
                                    title = screenItem.contentValue,
                                    onyomiReading = subject.buildReadingString("onyomi"),
                                    kunyomiReading = subject.buildReadingString("kunyomi"),
                                    nanoriReading = subject.buildReadingString("nanori"),
                                    readingText = subject.readingMnemonic,
                                    readingHint = subject.readingHint,
                                )
                            }
                            if (subject is Subject.Vocab) {
                                ReadingCard(
                                    title = screenItem.contentValue,
                                    mediaPlayer = mediaPlayer,
                                    primaryReading = subject.getPrimaryReading(),
                                    pronunciationAudios = subject.pronunciationAudios,
                                    readingText = subject.readingMnemonic
                                )
                            }
                        }
                        is DetailScreenItem.ContextItem -> {
                            if (subject is Subject.Vocab) {
                                ContextCard(
                                    title = screenItem.contentValue,
                                    contextSentences = subject.contextSentences
                                )
                            }
                        }
                        is DetailScreenItem.ProgressItem -> {
                            ProgressCard(
                                title = screenItem.contentValue,
                                isRadical = subject is Subject.Radical,
                                assignment = assignment,
                                reviewStatistics = reviewStatistics,
                                color = color
                            )
                        }
                        is DetailScreenItem.KanjiUsageItem, is DetailScreenItem.VocabUsageItem, is DetailScreenItem.SimilarKanjiItem -> {
                            val connectionsList =
                                if (screenItem is DetailScreenItem.SimilarKanjiItem) connections.visuallySimilarSubjects
                                else connections.amalgamationSubjects

                            ConnectionsList(
                                numberOfColumns = if (connectionsList.first() is Subject.Vocab) 1 else 4,
                                title = screenItem.contentValue,
                                connections = connectionsList,
                                imageLoader = imageLoader,
                                navigateToDetailScreen = navigateToDetailScreen,
                            )
                        }
                    }
                }
            }
        }
    }
}