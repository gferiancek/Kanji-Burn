package com.gavinferiancek.ui_subjectdetail.ui

import com.gavinferiancek.core.domain.ProgressBarState
import com.gavinferiancek.subject_domain.Subject
import com.gavinferiancek.ui_subjectdetail.R

data class SubjectDetailState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val subject: Subject? = null,
    val srsImageResource: Int = R.drawable.apprentice,
    val srsTitle: String = "Apprentice I",
)
