package com.gavinferiancek.login_datasource.repository

import com.gavinferiancek.core_cache.cache.KanjiBurnDatabase
import com.gavinferiancek.core_domain.reviewstatistics.ReviewStatistics
import com.gavinferiancek.core_domain.subject.Subject
import com.gavinferiancek.core_network.assignments_endpoint.AssignmentsService
import com.gavinferiancek.core_network.reviewstatistics_endpoint.ReviewStatisticsService
import com.gavinferiancek.core_network.subjects_endpoint.SubjectsService
import com.gavinferiancek.corecache.cache.AssignmentEntity
import com.gavinferiancek.corecache.cache.ReviewStatisticsEntity
import com.gavinferiancek.corecache.cache.SubjectEntity
import com.gavinferiancek.corecache.cache.SubjectEntityQueries

interface LoginRepository {

    suspend fun fetchAllSubjects(): List<SubjectEntity>

    suspend fun getAllSubjectsFromCache(): List<Subject>

    suspend fun fetchAllReviewStatistics(): List<ReviewStatisticsEntity>

    suspend fun getAllReviewStatisticsFromCache(): List<ReviewStatisticsEntity>

    suspend fun fetchAllAssignments(): List<AssignmentEntity>

    suspend fun getAllAssignmentsFromCache(): List<AssignmentEntity>

    fun insertSubject(subjectEntity: SubjectEntity)

    suspend fun insertAllSubjects(subjectEntities: List<SubjectEntity>)

    fun insertReviewStatistic(reviewStatisticsEntity: ReviewStatisticsEntity)

    suspend fun insertAllReviewStatistics(reviewStatisticsEntities: List<ReviewStatisticsEntity>)

    fun insertAssignment(assignmentEntity: AssignmentEntity)

    suspend fun insertAllAssignments(assignmentEntities: List<AssignmentEntity>)

    companion object Factory {
        fun build(
            database: KanjiBurnDatabase,
            subjectsService: SubjectsService,
            reviewStatisticsService: ReviewStatisticsService,
            assignmentsService: AssignmentsService,
        ): LoginRepository {
            return LoginRepositoryImpl(
                database = database,
                subjectsService = subjectsService,
                reviewStatisticsService = reviewStatisticsService,
                assignmentsService = assignmentsService,
            )
        }
    }
}