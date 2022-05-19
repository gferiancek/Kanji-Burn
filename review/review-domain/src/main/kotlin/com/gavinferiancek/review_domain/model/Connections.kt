package com.gavinferiancek.review_domain.model

import com.gavinferiancek.core_domain.subject.Subject

data class Connections(
    val amalgamationSubjects: List<Subject>,
    val componentSubjects: List<Subject>,
    val visuallySimilarSubjects: List<Subject>,
)
