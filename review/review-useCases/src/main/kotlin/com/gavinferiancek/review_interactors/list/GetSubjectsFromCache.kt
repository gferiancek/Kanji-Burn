package com.gavinferiancek.review_interactors.list

import com.gavinferiancek.core_domain.state.DataState
import com.gavinferiancek.core_domain.state.ProgressBarState
import com.gavinferiancek.core_domain.UIComponent
import com.gavinferiancek.core_domain.subject.Kanji
import com.gavinferiancek.core_domain.subject.Radical
import com.gavinferiancek.review_data.cache.ReviewCache
import com.gavinferiancek.core_domain.subject.Subject
import com.gavinferiancek.core_domain.subject.Vocab
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSubjectsFromCache(
    val cache: ReviewCache,
) {
    fun execute(): Flow<DataState<List<List<Subject>>>> = flow {
        try {
            val instancedSubjects = mutableListOf<List<Subject>>()

                val cachedSubjects = cache.getAllSubjects()
                // SubjectListScreen uses a ViewPager to show separate list for each type of subject.
                // We return a list of lists so that we can sync that to the TabRow. (1st tab = first list, etc)
                instancedSubjects.addAll(
                    listOf(
                        cachedSubjects.filterIsInstance<Radical>(),
                        cachedSubjects.filterIsInstance<Kanji>(),
                        cachedSubjects.filterIsInstance<Vocab>(),
                    )
                )
            emit(DataState.Data(data = instancedSubjects.toList()))
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