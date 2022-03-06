package com.gavinferiancek.ui_reviewList.ui

import com.gavinferiancek.core_domain.state.UIComponentState
import com.gavinferiancek.review_domain.SubjectFilter

sealed class ReviewListEvents {

    object FilterSubjects : ReviewListEvents()

    data class GetSubjects(
        val apiKey: String,
    ): ReviewListEvents()

    data class UpdateSearchQuery(
        val query: String,
    ): ReviewListEvents()

    data class UpdateFilterDialogState(
        val uiComponentState: UIComponentState,
    ): ReviewListEvents()

    data class UpdateReviewFilter(
        val subjectFilter: SubjectFilter,
    ): ReviewListEvents()

    data class UpdateTabData(
        val tabData: List<String>
    ): ReviewListEvents()
}
