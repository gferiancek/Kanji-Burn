package com.gavinferiancek.ui_subjectlist.ui

import com.gavinferiancek.core.domain.ProgressBarState
import com.gavinferiancek.core.domain.UIComponentState
import com.gavinferiancek.subject_domain.Subject
import com.gavinferiancek.subject_domain.SubjectFilter

data class SubjectListState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val subjects: List<List<Subject>> = listOf(),
    val filteredSubjects: List<List<Subject>> = listOf(),
    val query: String = "",
    val subjectFilter: SubjectFilter = SubjectFilter.Level(),
    val filterDialogState: UIComponentState = UIComponentState.Hidden,
    val tabData: List<String> = listOf("Radical", "Kanji", "Vocab"),
)
