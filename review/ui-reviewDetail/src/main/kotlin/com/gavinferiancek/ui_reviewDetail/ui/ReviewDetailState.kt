package com.gavinferiancek.ui_reviewDetail.ui

import com.gavinferiancek.core_domain.state.ProgressBarState
import com.gavinferiancek.core_domain.subject.Subject
import com.gavinferiancek.review_domain.model.Connections
import com.gavinferiancek.review_domain.model.ReviewSubject
import com.gavinferiancek.core_ui.SrsStageData
import com.gavinferiancek.review_domain.DetailScreenItem

data class ReviewDetailState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val reviewSubject: ReviewSubject? = null,
    val connections: Connections? = null,
    val srsStageData: SrsStageData = SrsStageData.Unknown()
) {
    fun getDetailScreenItems(): List<DetailScreenItem> {
        return when(reviewSubject?.subject) {
            is Subject.Radical -> buildList {
                    add(DetailScreenItem.NameItem())
                    if (reviewSubject.subject.unlocked) add(DetailScreenItem.ProgressItem())
                    add(DetailScreenItem.KanjiUsageItem())
            }
            is Subject.Kanji -> buildList {
                    add(DetailScreenItem.RadicalCompositionItem())
                    add(DetailScreenItem.MeaningItem())
                    add(DetailScreenItem.ReadingItem())
                    if (reviewSubject.subject.unlocked) add(DetailScreenItem.ProgressItem())
                    if (connections != null && connections.visuallySimilarSubjects.isNotEmpty()) add(DetailScreenItem.SimilarKanjiItem())
                    add(DetailScreenItem.VocabUsageItem())
            }
            is Subject.Vocab -> buildList {
                add(DetailScreenItem.KanjiCompositionItem())
                add(DetailScreenItem.MeaningItem())
                add(DetailScreenItem.ReadingItem())
                add(DetailScreenItem.ContextItem())
                if (reviewSubject.subject.unlocked) add(DetailScreenItem.ProgressItem())
            }
            else -> listOf()
        }
    }
}