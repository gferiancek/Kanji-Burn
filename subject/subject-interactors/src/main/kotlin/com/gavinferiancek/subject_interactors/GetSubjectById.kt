package com.gavinferiancek.subject_interactors

import com.gavinferiancek.core.domain.DataState
import com.gavinferiancek.core.domain.ProgressBarState
import com.gavinferiancek.core.domain.UIComponent
import com.gavinferiancek.subject_datasource.cache.SubjectCache
import com.gavinferiancek.subject_datasource.network.SubjectService
import com.gavinferiancek.subject_datasource.network.model.toSubject
import com.gavinferiancek.subject_datasource.network.model.toSubjectList
import com.gavinferiancek.subject_domain.Subject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSubjectById(
    val cache: SubjectCache,
) {
    fun execute(id: Int): Flow<DataState<Subject>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            val subject = cache.getSubjectById(id.toLong())
            println(subject.toString())
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