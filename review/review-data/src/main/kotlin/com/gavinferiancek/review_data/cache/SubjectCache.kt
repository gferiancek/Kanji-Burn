package com.gavinferiancek.review_data.cache


import com.gavinferiancek.core_domain.subject.Subject
import com.gavinferiancek.corecache.cache.SubjectEntityQueries
import com.gavinferiancek.review_domain.model.ReviewSubject

interface SubjectCache {

    suspend fun getAllSubjects(): List<Subject>

    suspend fun getReviewSubjectById(id: Long): ReviewSubject?

    suspend fun getSubjectsById(ids: List<Long>): List<Subject>

    companion object Factory {
        fun build(queries: SubjectEntityQueries): SubjectCache {
            return SubjectCacheImpl(queries)
        }
    }
}