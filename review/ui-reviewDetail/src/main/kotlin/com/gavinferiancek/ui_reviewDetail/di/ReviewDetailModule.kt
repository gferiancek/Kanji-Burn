package com.gavinferiancek.ui_reviewDetail.di

import com.gavinferiancek.review_interactors.GetReviewSubjectFromCache
import com.gavinferiancek.review_interactors.SubjectInteractors
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
    fun provideGetSubjectById(
        interactors: SubjectInteractors
    ): GetReviewSubjectFromCache {
        return interactors.getReviewSubjectFromCache
    }
}