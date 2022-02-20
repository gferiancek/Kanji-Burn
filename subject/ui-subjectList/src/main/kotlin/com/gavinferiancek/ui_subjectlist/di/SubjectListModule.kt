package com.gavinferiancek.ui_subjectlist.di

import com.gavinferiancek.subject_interactors.FilterSubjects
import com.gavinferiancek.subject_interactors.GetSubjects
import com.gavinferiancek.subject_interactors.SubjectInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object SubjectListModule {

    @Provides
    @ViewModelScoped
    fun provideGetSubjects(
        interactors: SubjectInteractors,
    ): GetSubjects {
        return interactors.getSubjects
    }

    @Provides
    @ViewModelScoped
    fun provideFilterSubjects(
        interactors: SubjectInteractors,
    ): FilterSubjects {
        return interactors.filterSubjects
    }
}