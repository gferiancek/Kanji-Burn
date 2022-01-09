package com.gavinferiancek.subject_interactors

import com.gavinferiancek.core.domain.DataState
import com.gavinferiancek.core.domain.ProgressBarState
import com.gavinferiancek.core.domain.UIComponent
import com.gavinferiancek.subject_datasource.network.SubjectService
import com.gavinferiancek.subject_datasource.network.model.toSubject
import com.gavinferiancek.subject_datasource.network.model.toSubjectList
import com.gavinferiancek.subject_domain.Subject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSubjects(
    val service: SubjectService,
) {
    fun execute(): Flow<DataState<List<Subject>>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            val apiKey = "1b74da00-b68f-4a4a-8e96-b6638d706013"
            val subjects = mutableListOf<Subject>()
            var response = service.getSubjects(apiKey, null)
            var page = 1
            var nextUrl = response.pageData.nextUrl
            subjects.addAll(response.data.toSubjectList())
            while (nextUrl != null) {
                response = service.getSubjects(apiKey, page * 1000)
                nextUrl = response.pageData.nextUrl
                page += 1
                subjects.addAll(response.data.toSubjectList())
            }
            emit(DataState.Data(data = subjects.toList()))
        } catch(e: Exception) {
            emit(DataState.Response(
                uiComponent = UIComponent.None(
                    message = e.message?: "Unknown error",
                )))
        }
        finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }
}