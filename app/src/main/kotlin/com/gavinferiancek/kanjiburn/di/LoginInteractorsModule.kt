package com.gavinferiancek.kanjiburn.di

import com.gavinferiancek.core_cache.cache.KanjiBurnDatabase
import com.gavinferiancek.core_network.assignments_endpoint.AssignmentsService
import com.gavinferiancek.core_network.reviewstatistics_endpoint.ReviewStatisticsService
import com.gavinferiancek.core_network.studymaterials_endpoint.StudyMaterialsService
import com.gavinferiancek.core_network.subjects_endpoint.SubjectsService
import com.gavinferiancek.login_datasource.repository.LoginRepository
import com.gavinferiancek.login_datasource.repository.LoginRepositoryImpl
import com.gavinferiancek.login_interactors.LoginInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginInteractorsModule {

    @Provides
    @Singleton
    fun provideLoginRepository(
        database: KanjiBurnDatabase,
    ): LoginRepository {
        return LoginRepositoryImpl(
            database = database,
            subjectsService = SubjectsService.build(),
            reviewStatisticsService = ReviewStatisticsService.build(),
            assignmentsService = AssignmentsService.build(),
            studyMaterialsService = StudyMaterialsService.build(),
        )
    }

    @Provides
    @Singleton
    fun provideLoginInteractors(
        repository: LoginRepository
    ): LoginInteractors {
        return LoginInteractors.build(repository = repository)
    }
}