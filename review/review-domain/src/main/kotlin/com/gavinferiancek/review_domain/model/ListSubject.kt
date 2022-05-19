package com.gavinferiancek.review_domain.model

import com.gavinferiancek.core_domain.assignment.Assignment
import com.gavinferiancek.core_domain.reviewstatistics.ReviewStatistics
import com.gavinferiancek.core_domain.subject.Subject

data class ListSubject(
    val subject: Subject,
    val assignment: Assignment,
)

