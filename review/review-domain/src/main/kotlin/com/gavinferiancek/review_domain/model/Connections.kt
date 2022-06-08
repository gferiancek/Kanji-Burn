package com.gavinferiancek.review_domain.model

import com.gavinferiancek.core_domain.subject.Subject

data class Connections(
    val amalgamationSubjects: List<Subject> = listOf(),
    val componentSubjects: List<Subject> = listOf(),
    val visuallySimilarSubjects: List<Subject> = listOf(),
)
