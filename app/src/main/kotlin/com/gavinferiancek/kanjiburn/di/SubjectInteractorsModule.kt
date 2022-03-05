package com.gavinferiancek.kanjiburn.di

import com.gavinferiancek.core_cache.cache.KanjiBurnDatabase
import com.gavinferiancek.subject_datasource.cache.SubjectCache
import com.gavinferiancek.subject_datasource.cache.SubjectCacheImpl
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
    fun provideSubjectCache(database: KanjiBurnDatabase): SubjectCache {
        return SubjectCacheImpl(database)
    }

    @Provides
    @Singleton
    fun provideSubjectInteractors(
        cache: SubjectCache,
    ): SubjectInteractors {
        return SubjectInteractors.build(cache)
    }
}