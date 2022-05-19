package com.gavinferiancek.ui_reviewDetail.ui

import com.gavinferiancek.core_domain.studymaterials.StudyMaterials
import com.gavinferiancek.core_domain.subject.Subject

sealed class ReviewDetailEvents {
    data class GetReviewSubject(
        val id: Int,
    ): ReviewDetailEvents()

    data class GetConnections(
        val subject: Subject
    ): ReviewDetailEvents()

    data class UpdateStudyMaterials(
        val studyMaterials: StudyMaterials,
    ): ReviewDetailEvents()
}
