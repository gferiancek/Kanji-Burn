package com.gavinferiancek.review_interactors

import com.gavinferiancek.review_data.cache.SubjectCache

data class SubjectInteractors(
    val getSubjectsFromCache: GetSubjectsFromCache,
    val getReviewSubjectFromCache: GetReviewSubjectFromCache,
    val filterSubjects: FilterSubjects,
) {
    companion object Factory {
        fun build(cache: SubjectCache ): SubjectInteractors {


            return SubjectInteractors(
                getSubjectsFromCache = GetSubjectsFromCache(
                    cache = cache,
                ),
                getReviewSubjectFromCache = GetReviewSubjectFromCache(
                    cache = cache
                ),
                filterSubjects = FilterSubjects(),
            )
        }
    }
}