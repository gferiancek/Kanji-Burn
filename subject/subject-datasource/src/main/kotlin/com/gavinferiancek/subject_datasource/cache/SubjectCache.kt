package com.gavinferiancek.subject_datasource.cache


import com.gavinferiancek.core_cache.cache.KanjiBurnDatabase
import com.gavinferiancek.corecache.cache.SubjectEntity
import com.gavinferiancek.subject_domain.*

interface SubjectCache {

    suspend fun getSubjectById(id: Long): Subject?

    suspend fun getAllSubjects(): List<Subject>

    suspend fun deleteSubjectById(id: Long)

    suspend fun insertSubject(subject: SubjectEntity)

    suspend fun insertAllSubjects(subjects: List<SubjectEntity>)

    companion object Factory {
        fun build(database: KanjiBurnDatabase): SubjectCache {
            return SubjectCacheImpl(database)
        }
    }
}
