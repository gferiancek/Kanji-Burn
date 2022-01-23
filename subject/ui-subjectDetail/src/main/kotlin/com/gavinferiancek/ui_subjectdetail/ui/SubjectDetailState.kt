package com.gavinferiancek.ui_subjectdetail.ui

import androidx.compose.ui.graphics.Color
import com.gavinferiancek.core.domain.ProgressBarState
import com.gavinferiancek.kanjiburn.ui.theme.KanjiBurnTheme
import com.gavinferiancek.subject_domain.Kanji
import com.gavinferiancek.subject_domain.Radical
import com.gavinferiancek.subject_domain.Subject
import com.gavinferiancek.subject_domain.Vocab
import com.gavinferiancek.ui_subjectdetail.R

data class SubjectDetailState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val subject: Subject? = null,
    val srsImageResource: Int = R.drawable.apprentice,
    val srsTitle: String = "Apprentice I",
)
