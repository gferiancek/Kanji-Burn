package com.gavinferiancek.subject_interactors

import com.gavinferiancek.subject_datasource.cache.SubjectCache
import com.gavinferiancek.subject_datasource.network.SubjectService
import com.squareup.sqldelight.db.SqlDriver

data class SubjectInteractors(
    val getSubjects: GetSubjects,
    val getSubjectById: GetSubjectById,
    val filterSubjects: FilterSubjects,
) {
    companion object Factory {
        fun build(cache: SubjectCache ): SubjectInteractors {
            val service = SubjectService.build()


            return SubjectInteractors(
                getSubjects = GetSubjects(
                    service = service,
                    cache = cache,
                ),
                getSubjectById = GetSubjectById(
                    cache = cache
                ),
                filterSubjects = FilterSubjects(),
            )
        }
    }
}