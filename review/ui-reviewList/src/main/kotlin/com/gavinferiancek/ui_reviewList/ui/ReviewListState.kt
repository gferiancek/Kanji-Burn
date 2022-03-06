package com.gavinferiancek.ui_reviewList.ui

import com.gavinferiancek.core_domain.state.ProgressBarState
import com.gavinferiancek.core_domain.state.UIComponentState
import com.gavinferiancek.core_domain.subject.Subject
import com.gavinferiancek.review_domain.SubjectFilter

data class ReviewListState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val subjects: List<List<Subject>> = listOf(),
    val filteredSubjects: List<List<Subject>> = listOf(),
    val query: String = "",
    val subjectFilter: SubjectFilter = SubjectFilter.Level(),
    val filterDialogState: UIComponentState = UIComponentState.Hidden,
    val tabData: List<String> = listOf("Radical", "Kanji", "Vocab"),
)
