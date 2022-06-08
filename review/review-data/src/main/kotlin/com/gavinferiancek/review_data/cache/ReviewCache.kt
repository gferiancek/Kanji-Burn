package com.gavinferiancek.review_data.cache


import com.gavinferiancek.core_domain.assignment.Assignment
import com.gavinferiancek.core_domain.reviewstatistics.ReviewStatistics
import com.gavinferiancek.core_domain.studymaterials.StudyMaterials
import com.gavinferiancek.core_domain.subject.Subject
import com.gavinferiancek.core_network.studymaterials_endpoint.StudyMaterialsService
import com.gavinferiancek.corecache.cache.*
import com.gavinferiancek.review_domain.model.ReviewSubject

interface ReviewCache {

    suspend fun getAllSubjects(): List<Subject>

    suspend fun getReviewSubjectById(id: Long): ReviewSubject?

    suspend fun getSubjectsById(ids: List<Long>): List<Subject>

    suspend fun getStudyMaterials(id: Long): StudyMaterials?

    suspend fun insertStudyMaterials(studyMaterialsEntity: StudyMaterialsEntity)

    companion object Factory {
        fun build(
            subjectQueries: SubjectEntityQueries,
            studyMaterialsQueries: StudyMaterialsEntityQueries,
        ): ReviewCache {
            return ReviewCacheImpl(subjectQueries, studyMaterialsQueries)
        }
    }
}