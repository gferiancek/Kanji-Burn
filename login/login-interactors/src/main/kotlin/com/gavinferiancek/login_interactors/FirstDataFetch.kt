package com.gavinferiancek.login_interactors

import com.gavinferiancek.core_domain.UIComponent
import com.gavinferiancek.core_domain.state.DataState
import com.gavinferiancek.core_domain.state.ProgressBarState
import com.gavinferiancek.login_datasource.repository.LoginRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FirstDataFetch(
    val repository: LoginRepository,
) {
    fun execute(): Flow<DataState<String>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            withContext(Dispatchers.IO) {
                val cachedSubjects = repository.getAllSubjectsFromCache()
                if (cachedSubjects.count() == 0) {
                    val subjects = repository.fetchAllSubjects()
                    repository.insertAllSubjects(subjects)
                    val reviewStatistics = repository.fetchAllReviewStatistics()
                    val assignments = repository.fetchAllAssignments()
                    repository.insertAllReviewStatistics(reviewStatistics)
                    repository.insertAllAssignments(assignments)
                }
            }
            emit(DataState.Data(data = "Completed"))
        } catch (e: Exception) {
            emit(
                DataState.Response(
                    uiComponent = UIComponent.None(
                        message = e.message ?: "Unknown error",
                    )
                )
            )
        } finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }
}