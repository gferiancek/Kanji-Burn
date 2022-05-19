package com.gavinferiancek.review_interactors.list

import com.gavinferiancek.core_domain.state.DataState
import com.gavinferiancek.core_domain.state.ProgressBarState
import com.gavinferiancek.core_domain.UIComponent
import com.gavinferiancek.review_data.cache.SubjectCache
import com.gavinferiancek.core_domain.subject.Subject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSubjectsFromCache(
    val cache: SubjectCache,
) {
    fun execute(): Flow<DataState<List<List<Subject>>>> = flow {
        try {
            val instancedSubjects = mutableListOf<List<Subject>>()

                val cachedSubjects = cache.getAllSubjects()
                // SubjectListScreen uses a ViewPager to show separate list for each type of subject.
                // We return a list of lists so that we can sync that to the TabRow. (1st tab = first list, etc)
                instancedSubjects.addAll(
                    listOf(
                        cachedSubjects.filterIsInstance<Subject.Radical>(),
                        cachedSubjects.filterIsInstance<Subject.Kanji>(),
                        cachedSubjects.filterIsInstance<Subject.Vocab>(),
                    )
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