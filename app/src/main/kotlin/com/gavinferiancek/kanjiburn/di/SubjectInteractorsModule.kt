package com.gavinferiancek.kanjiburn.di

import com.gavinferiancek.corecache.cache.SubjectEntityQueries
import com.gavinferiancek.review_data.cache.SubjectCache
import com.gavinferiancek.review_data.cache.SubjectCacheImpl
import com.gavinferiancek.review_interactors.SubjectInteractors
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
    fun provideSubjectCache(queries: SubjectEntityQueries): SubjectCache {
        return SubjectCacheImpl(queries)
    }

    @Provides
    @Singleton
    fun provideSubjectInteractors(
        cache: SubjectCache,
    ): SubjectInteractors {
        return SubjectInteractors.build(cache)
    }
}