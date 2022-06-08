package com.gavinferiancek.kanjiburn.di

import com.gavinferiancek.core_network.assignments_endpoint.AssignmentsService
import com.gavinferiancek.core_network.reviewstatistics_endpoint.ReviewStatisticsService
import com.gavinferiancek.core_network.studymaterials_endpoint.StudyMaterialsService
import com.gavinferiancek.core_network.subjects_endpoint.SubjectsService
import com.gavinferiancek.corecache.cache.AssignmentEntityQueries
import com.gavinferiancek.corecache.cache.ReviewStatisticsEntityQueries
import com.gavinferiancek.corecache.cache.StudyMaterialsEntityQueries
import com.gavinferiancek.corecache.cache.SubjectEntityQueries
import com.gavinferiancek.review_data.cache.ReviewCache
import com.gavinferiancek.review_interactors.ReviewUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReviewUseCasesModule {

    @Provides
    @Singleton
    fun provideReviewCache(
        subjectQueries: SubjectEntityQueries,
        studyMaterialsQueries: StudyMaterialsEntityQueries,
    ): ReviewCache {
        return ReviewCache.build(subjectQueries, studyMaterialsQueries)
    }

    @Provides
    @Singleton
    fun provideReviewUseCases(
        cache: ReviewCache,
    ): ReviewUseCases {
        return ReviewUseCases.build(
            cache = cache,
            studyMaterialsService = StudyMaterialsService.build(),
        )
    }
}