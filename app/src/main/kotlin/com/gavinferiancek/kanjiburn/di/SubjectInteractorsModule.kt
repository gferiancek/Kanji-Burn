package com.gavinferiancek.kanjiburn.di

import android.app.Application
import com.gavinferiancek.subject_interactors.SubjectInteractors
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
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
    // Look at just using SubjectDatabase.schema instead and using string for name.
    fun provideAndroidDriver(app: Application): SqlDriver {
        return AndroidSqliteDriver(
            schema = SubjectInteractors.schema,
            context = app,
            name = SubjectInteractors.databaseName,
        )
    }

    @Provides
    @Singleton
    fun provideSubjectInteractors(
        sqlDriver: SqlDriver,
    ): SubjectInteractors {
        return SubjectInteractors.build(
            sqlDriver = sqlDriver
        )
    }
}