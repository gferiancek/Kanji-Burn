package com.gavinferiancek.ui_reviewDetail.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import com.gavinferiancek.core_domain.UIComponent
import com.gavinferiancek.core_domain.state.ProgressBarState
import com.gavinferiancek.core_domain.state.UIComponentState
import com.gavinferiancek.core_domain.subject.Kanji
import com.gavinferiancek.core_domain.subject.Radical
import com.gavinferiancek.core_domain.subject.Vocab
import com.gavinferiancek.core_ui.SrsStageData
import com.gavinferiancek.core_ui.theme.kanji
import com.gavinferiancek.core_ui.theme.radical
import com.gavinferiancek.core_ui.theme.vocab
import com.gavinferiancek.core_domain.state.StudyMaterialsEditState
import com.gavinferiancek.review_domain.DetailScreenItem
import com.gavinferiancek.review_domain.model.Connections
import com.gavinferiancek.review_domain.model.ReviewSubject

data class ReviewDetailState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val snackbarComponent: UIComponent = UIComponent.SnackBar(messageResId = -1, action = ""),
    val snackbarState: UIComponentState = UIComponentState.Hidden,
    val reviewSubject: ReviewSubject? = null,
    val connections: Connections = Connections(),
    val editState: StudyMaterialsEditState = StudyMaterialsEditState.NotEditing,
    val textFieldValue: TextFieldValue = TextFieldValue()
)

@Composable
fun ReviewDetailState.getSubjectColor(): Color {
    return when(reviewSubject?.subject) {
        is Radical -> MaterialTheme.colors.radical
        is Kanji -> MaterialTheme.colors.kanji
        is Vocab -> MaterialTheme.colors.vocab
        else -> Color.Unspecified
    }
}

fun ReviewDetailState.getScreenItems(): List<DetailScreenItem> {
    return when (reviewSubject?.subject) {
        is Radical -> buildList {
            add(DetailScreenItem.NameItem())
            if (reviewSubject.subject.unlocked) add(DetailScreenItem.ProgressItem())
            add(DetailScreenItem.KanjiUsageItem())
        }
        is Kanji -> buildList {
            add(DetailScreenItem.RadicalCompositionItem())
            add(DetailScreenItem.MeaningItem())
            add(DetailScreenItem.ReadingItem())
            if (reviewSubject.subject.unlocked) add(DetailScreenItem.ProgressItem())
            if (connections.visuallySimilarSubjects.isNotEmpty()) add(DetailScreenItem.SimilarKanjiItem())
            add(DetailScreenItem.VocabUsageItem())
        }
        is Vocab -> buildList {
            add(DetailScreenItem.KanjiCompositionItem())
            add(DetailScreenItem.MeaningItem())
            add(DetailScreenItem.ReadingItem())
            add(DetailScreenItem.ContextItem())
            if (reviewSubject.subject.unlocked) add(DetailScreenItem.ProgressItem())
        }
        else -> listOf()
    }
}

fun ReviewDetailState.getSrsStageData(): SrsStageData {
    var srsStageData: SrsStageData = SrsStageData.Unknown()
    reviewSubject?.let { reviewSubject ->
        val unlocked = reviewSubject.subject.unlocked
        val notStarted = reviewSubject.assignment.startedAt == null

        srsStageData = when (reviewSubject.assignment.srsStage) {
            0 -> if (unlocked && notStarted) SrsStageData.Initiate() else SrsStageData.Unknown()
            1 -> SrsStageData.ApprenticeI()
            2 -> SrsStageData.ApprenticeII()
            3 -> SrsStageData.ApprenticeIII()
            4 -> SrsStageData.ApprenticeIV()
            5 -> SrsStageData.GuruI()
            6 -> SrsStageData.GuruII()
            7 -> SrsStageData.Master()
            8 -> SrsStageData.Enlightened()
            9 -> SrsStageData.Burned()
            else -> SrsStageData.Unknown()
        }
    }
    return srsStageData
}