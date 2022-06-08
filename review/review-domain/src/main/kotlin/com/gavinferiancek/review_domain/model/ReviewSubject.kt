package com.gavinferiancek.review_domain.model

import com.gavinferiancek.core_domain.assignment.Assignment
import com.gavinferiancek.core_domain.reviewstatistics.ReviewStatistics
import com.gavinferiancek.core_domain.studymaterials.StudyMaterials
import com.gavinferiancek.core_domain.subject.Subject

data class ReviewSubject(
    val subject: Subject,
    val reviewStatistics: ReviewStatistics,
    val assignment: Assignment,
    val studyMaterials: StudyMaterials,
)