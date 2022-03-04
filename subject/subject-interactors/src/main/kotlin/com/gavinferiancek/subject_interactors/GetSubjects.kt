package com.gavinferiancek.subject_interactors

import com.gavinferiancek.core.domain.DataState
import com.gavinferiancek.core.domain.ProgressBarState
import com.gavinferiancek.core.domain.UIComponent
import com.gavinferiancek.subject_datasource.cache.SubjectCache
import com.gavinferiancek.subject_datasource.network.EndPoints
import com.gavinferiancek.subject_datasource.network.subjects_endpoint.SubjectService
import com.gavinferiancek.subject_datasource.network.subjects_endpoint.model.toSubjectEntityList
import com.gavinferiancek.subject_domain.Subject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSubjects(
    val service: SubjectService,
    val cache: SubjectCache,
) {
    fun execute(): Flow<DataState<List<List<Subject>>>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            val apiKey = "1b74da00-b68f-4a4a-8e96-b6638d706013"
            val cachedSubjects = mutableListOf<Subject>()

            withContext(Dispatchers.IO) {
                cachedSubjects.addAll(cache.getAllSubjects())
                if (cachedSubjects.isEmpty()) {
                    var nextUrl: String? = EndPoints.SUBJECTS
                    val tasks = mutableListOf<Deferred<Unit>>()
                    while (nextUrl != null) {
                        val response = service.getSubjects(
                            apiKey = apiKey,
                            url = nextUrl
                        )
                        nextUrl = response.pageData.nextUrl
                        tasks.add(async { cache.insertAllSubjects(response.data.toSubjectEntityList()) })
                    }
                    tasks.awaitAll()
                    cachedSubjects.addAll(cache.getAllSubjects())
                }
            }
            // SubjectListScreen uses a ViewPager to show separate list for each type of subject.
            // We return a list of lists so that we can sync that to the TabRow. (1st tab = first list, etc)
            val instancedSubjects = listOf(
                cachedSubjects.filterIsInstance<Subject.Radical>(),
                cachedSubjects.filterIsInstance<Subject.Kanji>(),
                cachedSubjects.filterIsInstance<Subject.Vocab>(),
            )
            emit(DataState.Data(data = instancedSubjects))

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