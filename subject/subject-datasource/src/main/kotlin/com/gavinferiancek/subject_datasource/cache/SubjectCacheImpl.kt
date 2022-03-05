package com.gavinferiancek.subject_datasource.cache

import com.gavinferiancek.core_cache.cache.KanjiBurnDatabase
import com.gavinferiancek.corecache.cache.SubjectEntity
import com.gavinferiancek.subject_datasource.cache.model.toSubject
import com.gavinferiancek.subject_datasource.cache.model.toSubjectList
import com.gavinferiancek.subject_domain.Subject


class SubjectCacheImpl(
    database: KanjiBurnDatabase,
) : SubjectCache {

    private val queries = database.subjectEntityQueries

    override suspend fun getSubjectById(id: Long): Subject? {
        return queries.getSubjectById(id = id).executeAsOneOrNull()?.toSubject()
    }

    override suspend fun getAllSubjects(): List<Subject> {
        return queries.getAllSubjects().executeAsList().toSubjectList()
    }

    override suspend fun deleteSubjectById(id: Long) {
        queries.deleteSubjectFromId(id = id)
    }

    override suspend fun insertSubject(subject: SubjectEntity) {
        queries.insertSubject(subjectEntity = subject)
    }

    override suspend fun insertAllSubjects(subjects: List<SubjectEntity>) {
        subjects.forEach { subject ->
            insertSubject(subject)
        }
    }
}