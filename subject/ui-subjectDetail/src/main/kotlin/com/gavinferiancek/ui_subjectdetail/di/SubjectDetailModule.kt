package com.gavinferiancek.ui_subjectdetail.di

import com.gavinferiancek.subject_interactors.GetSubjectById
import com.gavinferiancek.subject_interactors.SubjectInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object SubjectDetailModule {

    @Provides
    @ViewModelScoped
    fun provideGetSubjectById(
        interactors: SubjectInteractors
    ): GetSubjectById {
        return interactors.getSubjectById
    }
}