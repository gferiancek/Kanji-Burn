package com.gavinferiancek.kanjiburn.di

import com.gavinferiancek.subject_interactors.SubjectInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SubjectInteractorsModule {

    @Provides
    @Singleton
    fun provideSubjectInteractors(): SubjectInteractors {
        return SubjectInteractors.build()
    }
}