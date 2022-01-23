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

class GetSubjectById(
    val service: SubjectService,
) {
    fun execute(id: Int): Flow<DataState<Subject>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            val apiKey = "1b74da00-b68f-4a4a-8e96-b6638d706013"
            val response = service.getSubjectById(
                apiKey = apiKey,
                id = id,
            )
            val subject: Subject = response.toSubject()
            emit(DataState.Data(data = subject))

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