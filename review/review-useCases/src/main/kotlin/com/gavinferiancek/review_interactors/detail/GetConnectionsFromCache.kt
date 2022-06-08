package com.gavinferiancek.review_interactors.detail

import com.gavinferiancek.core_domain.state.DataState
import com.gavinferiancek.core_domain.subject.Kanji
import com.gavinferiancek.core_domain.subject.Radical
import com.gavinferiancek.core_domain.subject.Subject
import com.gavinferiancek.core_domain.subject.Vocab
import com.gavinferiancek.review_data.cache.ReviewCache
import com.gavinferiancek.review_domain.model.Connections
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetConnectionsFromCache(
    val cache: ReviewCache,
) {
    fun execute(subject: Subject): Flow<DataState<Connections>> = flow {
        val connections = when (subject) {
            is Radical -> {
                Connections(
                    amalgamationSubjects = cache.getSubjectsById(subject.amalgamationSubjectIds.map { it.toLong() }),
                    componentSubjects = listOf(),
                    visuallySimilarSubjects = listOf(),
                )
            }
            is Kanji -> {
                Connections(
                    amalgamationSubjects = cache.getSubjectsById(subject.amalgamationSubjectIds.map { it.toLong() }),
                    componentSubjects = cache.getSubjectsById(subject.componentSubjectIds.map { it.toLong() }),
                    visuallySimilarSubjects = cache.getSubjectsById(subject.visuallySimilarSubjectIds.map { it.toLong() }),
                )
            }
            is Vocab -> {
                Connections(
                    amalgamationSubjects = listOf(),
                    componentSubjects = cache.getSubjectsById(subject.componentSubjectIds.map { it.toLong() }),
                    visuallySimilarSubjects = listOf(),
                )
            }
        }
        emit(DataState.Data(data = connections))
    }
}