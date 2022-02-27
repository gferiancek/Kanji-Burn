package com.gavinferiancek.subject_datasource.cache

import com.gavinferiancek.subject_datasource.cache.model.toSubject
import com.gavinferiancek.subject_datasource.cache.model.toSubjectList
import com.gavinferiancek.subject_domain.Subject
import com.gavinferiancek.subjectdatasource.cache.SubjectEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SubjectCacheImpl(
    database: SubjectDatabase,
) : SubjectCache {

    private val queries = database.subjectDbQueries
    override suspend fun getSubjectById(id: Long): Subject? {
        return withContext(Dispatchers.IO) {
            queries.getSubjectById(id = id).executeAsOneOrNull()?.toSubject()
        }
    }

    override suspend fun getAllSubjects(): List<Subject> {
        return withContext(Dispatchers.IO) {
            queries.getAllSubjects().executeAsList().toSubjectList()
        }
    }

    override suspend fun deleteSubjectById(id: Long) {
        withContext(Dispatchers.IO) {
            queries.deleteSubjectFromId(id = id)
        }
    }

    override suspend fun insertSubject(subject: SubjectEntity) {
        withContext(Dispatchers.IO) {
            queries.insertSubject(
                id = subject.id,
                type = subject.type,
                level = subject.level,
                characters = subject.characters,
                meanings = subject.meanings,
                auxiliaryMeanings = subject.auxiliaryMeanings,
                meaningMnemonic = subject.meaningMnemonic,
                lessonPosition = subject.lessonPosition,
                srsSystem = subject.lessonPosition,
                readings = subject.readings,
                meaningHint = subject.meaningHint,
                readingMnemonic = subject.readingMnemonic,
                readingHint = subject.readingHint,
                characterImage = subject.characterImage,
                amalgamationSubjectIds = subject.amalgamationSubjectIds,
                componentSubjectIds = subject.componentSubjectIds,
                visuallySimilarSubjectIds = subject.visuallySimilarSubjectIds,
                partsOfSpeech = subject.partsOfSpeech,
                contextSentences = subject.contextSentences,
                pronunciationAudios = subject.pronunciationAudios
            )
        }
    }

    override suspend fun insertAllSubjects(subjects: List<SubjectEntity>) {
        withContext(Dispatchers.IO) {
            subjects.forEach { subject ->
                insertSubject(subject)
            }
        }
    }
}