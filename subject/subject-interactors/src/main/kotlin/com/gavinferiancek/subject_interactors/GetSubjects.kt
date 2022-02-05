package com.gavinferiancek.subject_interactors

import com.gavinferiancek.core.domain.DataState
import com.gavinferiancek.core.domain.ProgressBarState
import com.gavinferiancek.core.domain.UIComponent
import com.gavinferiancek.subject_datasource.network.SubjectService
import com.gavinferiancek.subject_datasource.network.model.toSubjectList
import com.gavinferiancek.subject_domain.Kanji
import com.gavinferiancek.subject_domain.Radical
import com.gavinferiancek.subject_domain.Subject
import com.gavinferiancek.subject_domain.Vocab
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class GetSubjects(
    val service: SubjectService,
) {
    fun execute(): Flow<DataState<List<List<Subject>>>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            val apiKey = "1b74da00-b68f-4a4a-8e96-b6638d706013"
            val subjects = mutableListOf<Subject>()
            var page = 1

            withContext(Dispatchers.IO) {
                //When it comes to syncing up all of the data on the first load, use a list of kotlin async tasks.
                // You can awaitAll() to ensure all data is loaded before proceeding to the dashboard.
                // Example: https://www.baeldung.com/kotlin/coroutines-waiting-for-multiple-threads
                var response = service.getSubjects(apiKey, null)
                var nextUrl = response.pageData.nextUrl
                subjects.addAll(response.data.toSubjectList())
                while (nextUrl != null) {
                    response = service.getSubjects(apiKey, page * 1000)
                    nextUrl = response.pageData.nextUrl
                    page += 1
                    subjects.addAll(response.data.toSubjectList())
                }
            }
            // SubjectListScreen uses a ViewPager to show separate list for each type of subject.
            // We return a list of lists so that we can sync that to the TabRow. (1st tab = first list, etc)
            val instancedSubjects = listOf(
                subjects.filterIsInstance<Radical>(),
                subjects.filterIsInstance<Kanji>(),
                subjects.filterIsInstance<Vocab>(),
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