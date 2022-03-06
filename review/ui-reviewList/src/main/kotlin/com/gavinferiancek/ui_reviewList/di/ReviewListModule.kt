package com.gavinferiancek.ui_reviewList.di

import com.gavinferiancek.review_interactors.FilterSubjects
import com.gavinferiancek.review_interactors.GetSubjectsFromCache
import com.gavinferiancek.review_interactors.SubjectInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ReviewListModule {

    @Provides
    @ViewModelScoped
    fun provideGetSubjectsFromCache(
        interactors: SubjectInteractors,
    ): GetSubjectsFromCache {
        return interactors.getSubjectsFromCache
    }

    @Provides
    @ViewModelScoped
    fun provideFilterSubjects(
        interactors: SubjectInteractors,
    ): FilterSubjects {
        return interactors.filterSubjects
    }
}