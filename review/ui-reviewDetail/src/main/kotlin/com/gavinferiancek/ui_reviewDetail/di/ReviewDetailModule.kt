package com.gavinferiancek.ui_reviewDetail.di

import com.gavinferiancek.review_interactors.detail.GetReviewSubjectFromCache
import com.gavinferiancek.review_interactors.SubjectUseCases
import com.gavinferiancek.review_interactors.detail.GenerateSrsStageData
import com.gavinferiancek.review_interactors.detail.GetConnectionsFromCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ReviewDetailModule {

    @Provides
    @ViewModelScoped
    fun provideGetSubjectById(useCases: SubjectUseCases): GetReviewSubjectFromCache =
        useCases.getReviewSubjectFromCache

    @Provides
    @ViewModelScoped
    fun provideGetConnectionsFromCache(useCases: SubjectUseCases): GetConnectionsFromCache =
        useCases.getConnectionsFromCache

    @Provides
    @ViewModelScoped
    fun provideGenerateSrsStageData(useCases: SubjectUseCases): GenerateSrsStageData =
        useCases.generateSrsStageData
}