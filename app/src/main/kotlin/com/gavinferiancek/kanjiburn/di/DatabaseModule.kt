package com.gavinferiancek.kanjiburn.di

import android.app.Application
import com.gavinferiancek.core_cache.*
import com.gavinferiancek.core_cache.cache.KanjiBurnDatabase
import com.gavinferiancek.corecache.cache.*
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAndroidDriver(app: Application): SqlDriver {
        return AndroidSqliteDriver(
            schema = KanjiBurnDatabase.Schema,
            context = app,
            name = "kanjiBurn.db",
        )
    }

    @Provides
    @Singleton
    fun provideKanjiBurnDatabase(sqlDriver: SqlDriver): KanjiBurnDatabase {
        return KanjiBurnDatabase(
            driver = sqlDriver,
            subjectEntityAdapter = SubjectEntity.Adapter(
                meaningsAdapter = meaningsAdapter,
                auxiliaryMeaningsAdapter = auxiliaryMeaningsAdapter,
                readingsAdapter = readingsAdapter,
                amalgamationSubjectIdsAdapter = idListAdapter,
                componentSubjectIdsAdapter = idListAdapter,
                visuallySimilarSubjectIdsAdapter = idListAdapter,
                partsOfSpeechAdapter = stringListAdapter,
                contextSentencesAdapter = contextSentencesAdapter,
                pronunciationAudiosAdapter = pronunciationAudiosAdapter,
            ),
            studyMaterialsEntityAdapter = StudyMaterialsEntity.Adapter(
                meaningSynonymsAdapter = stringListAdapter,
            )
        )
    }

    @Provides
    @Singleton
    fun provideSubjectQueries(database: KanjiBurnDatabase): SubjectEntityQueries =
        database.subjectEntityQueries

    @Provides
    @Singleton
    fun provideStudyMaterialsQueries(database: KanjiBurnDatabase): StudyMaterialsEntityQueries =
        database.studyMaterialsEntityQueries
}