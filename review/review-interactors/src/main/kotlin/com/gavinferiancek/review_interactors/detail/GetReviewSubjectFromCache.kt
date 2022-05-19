package com.gavinferiancek.review_interactors.detail

import com.gavinferiancek.core_domain.state.DataState
import com.gavinferiancek.core_domain.state.ProgressBarState
import com.gavinferiancek.core_domain.UIComponent
import com.gavinferiancek.core_domain.subject.Subject
import com.gavinferiancek.review_data.cache.SubjectCache
import com.gavinferiancek.review_domain.model.Connections
import com.gavinferiancek.review_domain.model.ReviewSubject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetReviewSubjectFromCache(
    val cache: SubjectCache,
) {
    fun execute(id: Int): Flow<DataState<ReviewSubject>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
            val reviewSubject = cache.getReviewSubjectById(id.toLong())
            reviewSubject?.let {

            }

            emit(DataState.Data(data = cache.getReviewSubjectById(id.toLong())))
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