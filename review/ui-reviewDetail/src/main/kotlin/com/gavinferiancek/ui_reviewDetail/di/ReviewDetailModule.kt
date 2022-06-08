package com.gavinferiancek.ui_reviewDetail.di

import com.gavinferiancek.review_interactors.detail.GetReviewSubjectFromCache
import com.gavinferiancek.review_interactors.ReviewUseCases
import com.gavinferiancek.review_interactors.detail.GetConnectionsFromCache
import com.gavinferiancek.review_interactors.detail.UpdateStudyMaterials
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
    fun provideGetSubjectById(useCases: ReviewUseCases): GetReviewSubjectFromCache =
        useCases.getReviewSubjectFromCache

    @Provides
    @ViewModelScoped
    fun provideGetConnectionsFromCache(useCases: ReviewUseCases): GetConnectionsFromCache =
        useCases.getConnectionsFromCache

    @Provides
    @ViewModelScoped
    fun provideUpdateStudyMaterials(useCases: ReviewUseCases): UpdateStudyMaterials =
        useCases.updateStudyMaterials
}