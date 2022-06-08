package com.gavinferiancek.login_interactors

import com.gavinferiancek.core_domain.UIComponent
import com.gavinferiancek.core_domain.state.DataState
import com.gavinferiancek.core_domain.state.ProgressBarState
import com.gavinferiancek.login_datasource.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class FirstDataFetch(
    val repository: LoginRepository,
) {
    fun execute(): Flow<DataState<String>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            withContext(Dispatchers.IO) {
                val cachedSubjects = repository.getAllSubjectsFromCache()
                if (cachedSubjects.isEmpty()) {
                    val subjectsTask = async {
                        repository.insertAllSubjects(repository.fetchAllSubjects())
                    }
                    val reviewStatistics = repository.fetchAllReviewStatistics()
                    val assignments = repository.fetchAllAssignments()
                    val studyMaterials = repository.fetchAllStudyMaterials()
                    subjectsTask.await()
                    repository.insertAllReviewStatistics(reviewStatistics)
                    repository.insertAllAssignments(assignments)
                    repository.insertAllStudyMaterials(studyMaterials)
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