package com.gavinferiancek.login_datasource.repository

import com.gavinferiancek.core_cache.cache.KanjiBurnDatabase
import com.gavinferiancek.core_cache.model.toSubjectList
import com.gavinferiancek.core_domain.reviewstatistics.ReviewStatistics
import com.gavinferiancek.core_domain.subject.Subject
import com.gavinferiancek.core_network.EndPoints
import com.gavinferiancek.core_network.assignments_endpoint.AssignmentsService
import com.gavinferiancek.core_network.assignments_endpoint.model.toAssignmentEntityList
import com.gavinferiancek.core_network.reviewstatistics_endpoint.ReviewStatisticsService
import com.gavinferiancek.core_network.reviewstatistics_endpoint.model.toReviewStatisticsEntityList
import com.gavinferiancek.core_network.subjects_endpoint.SubjectsService
import com.gavinferiancek.core_network.subjects_endpoint.model.toSubjectEntityList
import com.gavinferiancek.corecache.cache.AssignmentEntity
import com.gavinferiancek.corecache.cache.ReviewStatisticsEntity
import com.gavinferiancek.corecache.cache.SubjectEntity
import com.gavinferiancek.corecache.cache.SubjectEntityQueries
import kotlinx.coroutines.*

class LoginRepositoryImpl(
    private val database: KanjiBurnDatabase,
    private val subjectsService: SubjectsService,
    private val reviewStatisticsService: ReviewStatisticsService,
    private val assignmentsService: AssignmentsService,
) : LoginRepository {

    private val apiKey = "1b74da00-b68f-4a4a-8e96-b6638d706013"

    override suspend fun fetchAllSubjects(): List<SubjectEntity> {
        val subjectEntities: MutableList<SubjectEntity> = mutableListOf()
        withContext(Dispatchers.IO) {
            var nextUrl: String? = EndPoints.SUBJECTS
            while (nextUrl != null) {
                val response = subjectsService.getSubjects(
                    apiKey = apiKey,
                    url = nextUrl
                )
                nextUrl = response.pageData.nextUrl
                subjectEntities.addAll(response.data.toSubjectEntityList())
            }
        }
        return subjectEntities
    }

    override suspend fun getAllSubjectsFromCache(): List<Subject> {
        return database.subjectEntityQueries.getAllSubjects().executeAsList().toSubjectList()
    }

    override suspend fun fetchAllReviewStatistics(): List<ReviewStatisticsEntity> {
        val reviewStatisticsEntities: MutableList<ReviewStatisticsEntity> = mutableListOf()
        withContext(Dispatchers.IO) {
            var nextUrl: String? = EndPoints.REVIEW_STATISTICS
            while (nextUrl != null) {
                val response = reviewStatisticsService.getReviewStatistics(
                    apiKey = apiKey,
                    url = nextUrl
                )
                nextUrl = response.pageData.nextUrl
                reviewStatisticsEntities.addAll(response.data.toReviewStatisticsEntityList())
            }
        }
        return reviewStatisticsEntities
    }

    override suspend fun getAllReviewStatisticsFromCache(): List<ReviewStatisticsEntity> {
        return database.reviewStatisticsEntityQueries.getAllReviewStatistics().executeAsList()
    }

    override suspend fun fetchAllAssignments(): List<AssignmentEntity> {
        val assignmentEntities: MutableList<AssignmentEntity> = mutableListOf()
        withContext(Dispatchers.IO) {
            var nextUrl: String? = EndPoints.ASSIGNMENTS
            while (nextUrl != null) {
                val response = assignmentsService.getAssignments(
                    apiKey = apiKey,
                    url = nextUrl
                )
                nextUrl = response.pageData.nextUrl
                assignmentEntities.addAll(response.data.toAssignmentEntityList())
            }
        }
        return assignmentEntities
    }

    override suspend fun getAllAssignmentsFromCache(): List<AssignmentEntity> {
        return database.assignmentEntityQueries.getAllAssignments().executeAsList()
    }

    /**
     * General pattern for these functions: Suspend fun insertAll is called by use case on
     * Dispatchers.IO. Single insert fun is not a suspend fun, as it is run inside a Transaction
     * which is designed to remain on the thread that it was created on. (I.E. Dispatchers.IO
     */
    override fun insertSubject(subjectEntity: SubjectEntity) {
        database.subjectEntityQueries.insertSubject(subjectEntity)
    }

    override suspend fun insertAllSubjects(subjectEntities: List<SubjectEntity>) {
        database.transaction {
            subjectEntities.forEach { subjectEntity ->
                insertSubject(subjectEntity)
            }
        }
    }

    override fun insertReviewStatistic(reviewStatisticsEntity: ReviewStatisticsEntity) {
        database.reviewStatisticsEntityQueries.insertReviewStatistics(reviewStatisticsEntity)
    }

    override suspend fun insertAllReviewStatistics(reviewStatisticsEntities: List<ReviewStatisticsEntity>) {
            database.transaction {
                reviewStatisticsEntities.forEach { reviewStatisticsEntity ->
                    insertReviewStatistic(reviewStatisticsEntity)
                }
            }
    }

    override fun insertAssignment(assignmentEntity: AssignmentEntity) {
        database.assignmentEntityQueries.insertAssignment(assignmentEntity)
    }

    override suspend fun insertAllAssignments(assignmentEntities: List<AssignmentEntity>) {
        database.transaction {
            assignmentEntities.forEach { assignmentEntity ->
                insertAssignment(assignmentEntity)
            }
        }
    }

}