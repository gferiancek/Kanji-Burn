package com.gavinferiancek.subject_interactors

import com.gavinferiancek.core.domain.DataState
import com.gavinferiancek.core.domain.ProgressBarState
import com.gavinferiancek.core.domain.UIComponent
import com.gavinferiancek.subject_datasource.cache.SubjectCache
import com.gavinferiancek.subject_datasource.network.SubjectService
import com.gavinferiancek.subject_datasource.network.model.SubjectDtoWrapper
import com.gavinferiancek.subject_datasource.network.model.toSubjectEntityList
import com.gavinferiancek.subject_domain.Subject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class GetSubjects(
    val service: SubjectService,
    val cache: SubjectCache,
) {
    fun execute(): Flow<DataState<List<List<Subject>>>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            val apiKey = "1b74da00-b68f-4a4a-8e96-b6638d706013"
            val cachedSubjects = mutableListOf<Subject>()
            var page = 1

            withContext(Dispatchers.IO) {
                cachedSubjects.addAll(cache.getAllSubjects())
                //When it comes to syncing up all of the data on the first load, use a list of kotlin async tasks.
                // You can awaitAll() to ensure all data is loaded before proceeding to the dashboard.
                // Example: https://www.baeldung.com/kotlin/coroutines-waiting-for-multiple-threads
                if (cachedSubjects.isEmpty()) {
                    var response = service.getSubjects(apiKey, null)
                    var nextUrl = response.pageData.nextUrl
                    val subjects = mutableListOf<SubjectDtoWrapper>()
                    subjects.addAll(response.data)
                    while (nextUrl != null) {
                        response = service.getSubjects(apiKey, page * 1000)
                        nextUrl = response.pageData.nextUrl
                        page += 1
                        subjects.addAll(response.data)
                    }
                    cache.insertAllSubjects(subjects.toSubjectEntityList())
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