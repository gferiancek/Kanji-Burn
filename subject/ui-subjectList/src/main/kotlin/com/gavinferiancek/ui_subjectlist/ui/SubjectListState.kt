package com.gavinferiancek.ui_subjectlist.ui

import com.gavinferiancek.core.domain.ProgressBarState
import com.gavinferiancek.subject_domain.Subject

data class SubjectListState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val subjects: List<List<Subject>> = listOf(),
)
