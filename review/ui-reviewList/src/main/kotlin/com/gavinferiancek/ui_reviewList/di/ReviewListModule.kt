package com.gavinferiancek.ui_reviewList.di

import com.gavinferiancek.review_interactors.list.FilterSubjects
import com.gavinferiancek.review_interactors.list.GetSubjectsFromCache
import com.gavinferiancek.review_interactors.SubjectUseCases
import com.gavinferiancek.review_interactors.list.GetInnerSubjectListCounts
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
    fun provideGetSubjectsFromCache(useCases: SubjectUseCases): GetSubjectsFromCache =
        useCases.getSubjectsFromCache

    @Provides
    @ViewModelScoped
    fun provideFilterSubjects(useCases: SubjectUseCases): FilterSubjects =
        useCases.filterSubjects

    @Provides
    @ViewModelScoped
    fun provideGenerateTabData(useCases: SubjectUseCases): GetInnerSubjectListCounts =
        useCases.getInnerSubjectListCounts
}