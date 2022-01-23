package com.gavinferiancek.subject_interactors

import com.gavinferiancek.subject_datasource.network.SubjectService

data class SubjectInteractors(
    val getSubjects: GetSubjects,
    val getSubjectById: GetSubjectById
) {
    companion object Factory {
        fun build(): SubjectInteractors {
            val service = SubjectService.build()

            return SubjectInteractors(
                getSubjects = GetSubjects(
                    service = service,
                ),
                getSubjectById = GetSubjectById(
                    service = service
                )
            )
        }
    }
}