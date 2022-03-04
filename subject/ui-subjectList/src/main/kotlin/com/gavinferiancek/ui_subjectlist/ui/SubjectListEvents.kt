package com.gavinferiancek.ui_subjectlist.ui

import com.gavinferiancek.core.domain.UIComponentState
import com.gavinferiancek.subject_domain.SubjectFilter

sealed class SubjectListEvents {

    object FilterSubjects : SubjectListEvents()

    data class GetSubjects(
        val apiKey: String,
    ): SubjectListEvents()

    data class UpdateSearchQuery(
        val query: String,
    ): SubjectListEvents()

    data class UpdateFilterDialogState(
        val uiComponentState: UIComponentState,
    ): SubjectListEvents()

    data class UpdateSubjectFilter(
        val subjectFilter: SubjectFilter,
    ): SubjectListEvents()

    data class UpdateTabData(
        val tabData: List<String>
    ): SubjectListEvents()
}
