package com.gavinferiancek.review_interactors.detail

import com.gavinferiancek.core_domain.state.DataState
import com.gavinferiancek.core_domain.state.ProgressBarState
import com.gavinferiancek.core_domain.UIComponent
import com.gavinferiancek.core_network.assignments_endpoint.AssignmentsService
import com.gavinferiancek.core_network.reviewstatistics_endpoint.ReviewStatisticsService
import com.gavinferiancek.core_network.studymaterials_endpoint.StudyMaterialsService
import com.gavinferiancek.core_network.subjects_endpoint.SubjectsService
import com.gavinferiancek.review_data.cache.ReviewCache
import com.gavinferiancek.review_domain.model.ReviewSubject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetReviewSubjectFromCache(
    val cache: ReviewCache,
) {
    fun execute(id: Int): Flow<DataState<ReviewSubject>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            val reviewSubject = cache.getReviewSubjectById(id.toLong())
            emit(DataState.Data(data = reviewSubject))
        } catch (e: Exception) {
            emit(
                DataState.Response(
                    uiComponent = UIComponent.None(
                        message = e.message ?: "Unknown Error",
                    )
                )
            )
        } finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }
}