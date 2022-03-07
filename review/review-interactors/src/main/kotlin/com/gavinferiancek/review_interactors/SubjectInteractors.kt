package com.gavinferiancek.review_interactors

import com.gavinferiancek.review_data.cache.SubjectCache
import com.gavinferiancek.review_interactors.detail.GenerateSrsStageData
import com.gavinferiancek.review_interactors.detail.GetReviewSubjectFromCache
import com.gavinferiancek.review_interactors.list.FilterSubjects
import com.gavinferiancek.review_interactors.list.GetInnerSubjectListCounts
import com.gavinferiancek.review_interactors.list.GetSubjectsFromCache

data class SubjectInteractors(
    val getSubjectsFromCache: GetSubjectsFromCache,
    val getReviewSubjectFromCache: GetReviewSubjectFromCache,
    val filterSubjects: FilterSubjects,
    val getInnerSubjectListCounts: GetInnerSubjectListCounts,
    val generateSrsStageData: GenerateSrsStageData,
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
                getInnerSubjectListCounts = GetInnerSubjectListCounts(),
                generateSrsStageData = GenerateSrsStageData(),
            )
        }
    }
}