package com.gavinferiancek.review_interactors

import com.gavinferiancek.core_network.studymaterials_endpoint.StudyMaterialsService
import com.gavinferiancek.review_data.cache.ReviewCache
import com.gavinferiancek.review_interactors.detail.GetConnectionsFromCache
import com.gavinferiancek.review_interactors.detail.GetReviewSubjectFromCache
import com.gavinferiancek.review_interactors.detail.UpdateStudyMaterials
import com.gavinferiancek.review_interactors.list.FilterSubjects
import com.gavinferiancek.review_interactors.list.GetInnerSubjectListCounts
import com.gavinferiancek.review_interactors.list.GetSubjectsFromCache

data class ReviewUseCases(
    val getSubjectsFromCache: GetSubjectsFromCache,
    val getReviewSubjectFromCache: GetReviewSubjectFromCache,
    val getConnectionsFromCache: GetConnectionsFromCache,
    val filterSubjects: FilterSubjects,
    val getInnerSubjectListCounts: GetInnerSubjectListCounts,
    val updateStudyMaterials: UpdateStudyMaterials,
) {
    companion object Factory {
        fun build(
            cache: ReviewCache,
            studyMaterialsService: StudyMaterialsService
        ): ReviewUseCases {
            return ReviewUseCases(
                getSubjectsFromCache = GetSubjectsFromCache(cache = cache),
                getReviewSubjectFromCache = GetReviewSubjectFromCache(cache = cache),
                getConnectionsFromCache = GetConnectionsFromCache(cache = cache),
                filterSubjects = FilterSubjects(),
                getInnerSubjectListCounts = GetInnerSubjectListCounts(),
                updateStudyMaterials = UpdateStudyMaterials(
                    cache = cache,
                    service = studyMaterialsService,
                ),
            )
        }
    }
}