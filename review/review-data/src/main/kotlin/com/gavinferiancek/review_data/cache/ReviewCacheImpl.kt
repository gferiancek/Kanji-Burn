package com.gavinferiancek.review_data.cache

import com.gavinferiancek.core_cache.model.toStudyMaterials
import com.gavinferiancek.core_cache.model.toSubjectList
import com.gavinferiancek.core_domain.assignment.Assignment
import com.gavinferiancek.core_domain.reviewstatistics.ReviewStatistics
import com.gavinferiancek.core_domain.studymaterials.StudyMaterials
import com.gavinferiancek.core_domain.subject.Kanji
import com.gavinferiancek.core_domain.subject.Radical
import com.gavinferiancek.core_domain.subject.Subject
import com.gavinferiancek.core_domain.subject.Vocab
import com.gavinferiancek.corecache.cache.*
import com.gavinferiancek.review_domain.model.ReviewSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReviewCacheImpl(
    private val subjectQueries: SubjectEntityQueries,
    private val studyMaterialsQueries: StudyMaterialsEntityQueries,
) : ReviewCache {

    override suspend fun getAllSubjects(): List<Subject> {
        var subjects: List<Subject>
        withContext(Dispatchers.IO) {
            subjects = subjectQueries.getAllBaseSubjects().executeAsList().toSubjectList()
        }
        return subjects
    }

    override suspend fun getReviewSubjectById(id: Long): ReviewSubject {
        return subjectQueries.getReviewSubjectById(id).executeAsOne().toReviewSubject()
    }

    override suspend fun getSubjectsById(ids: List<Long>): List<Subject> {
        return subjectQueries.getBaseSubjectsById(ids).executeAsList().toSubjectList()
    }

    override suspend fun getStudyMaterials(id: Long): StudyMaterials? {
        return studyMaterialsQueries.getStudyMaterialsById(id).executeAsOneOrNull()?.toStudyMaterials()
    }

    override suspend fun insertStudyMaterials(studyMaterialsEntity: StudyMaterialsEntity) {
        studyMaterialsQueries.insertStudyMaterials(studyMaterialsEntity)
    }
}

fun ReviewSubjectEntity.toReviewSubject(): ReviewSubject {
    return when (type) {
        "radical" -> {
            ReviewSubject(
                subject = Radical(
                    id = id.toInt(),
                    level = level.toInt(),
                    characters = characters,
                    meanings = meanings,
                    auxiliaryMeanings = auxiliaryMeanings,
                    meaningMnemonic = meaningMnemonic,
                    lessonPosition = lessonPosition.toInt(),
                    srsSystem = srsSystem.toInt(),
                    amalgamationSubjectIds = amalgamationSubjectIds,
                    unlocked = unlockedAt != null,
                ),
                reviewStatistics = ReviewStatistics(
                    id = reviewStatisticsId?.toInt() ?: -1,
                    meaningCorrect = meaningCorrect?.toInt() ?: 0,
                    meaningIncorrect = meaningIncorrect?.toInt() ?: 0,
                    meaningMaxStreak = meaningMaxStreak?.toInt() ?: 0,
                    meaningCurrentStreak = meaningCurrentSteak?.toInt() ?: 0,
                    readingCorrect = readingCorrect?.toInt() ?: 0,
                    readingIncorrect = readingIncorrect?.toInt() ?: 0,
                    readingMaxStreak = readingMaxStreak?.toInt() ?: 0,
                    readingCurrentStreak = readingCurrentStreak?.toInt() ?: 0,
                    percentageCorrect = percentageCorrect?.toInt() ?: 0,
                    hidden = hidden == 1L
                ),
                assignment = Assignment(
                    id = assignmentId?.toInt() ?: -1,
                    srsStage = srsStage?.toInt() ?: 0,
                    unlockedAt = unlockedAt,
                    startedAt = startedAt,
                    passedAt = passedAt,
                    burnedAt = burnedAt,
                    availableAt = availableAt,
                    resurrectedAt = resurrectedAt,
                    hidden = hidden == 1L,
                ),
                studyMaterials = StudyMaterials(
                    id = studyMaterialsId?.toInt() ?: -1,
                    subjectId = id.toInt(),
                    meaningNote = meaningNote ?: "",
                    readingNote = readingNote ?: "",
                    meaningSynonyms = meaningSynonyms ?: listOf(),
                ),
            )
        }
        "kanji" -> {
            ReviewSubject(
                subject = Kanji(
                    id = id.toInt(),
                    level = level.toInt(),
                    characters = characters,
                    meanings = meanings,
                    auxiliaryMeanings = auxiliaryMeanings,
                    meaningMnemonic = meaningMnemonic,
                    lessonPosition = lessonPosition.toInt(),
                    srsSystem = srsSystem.toInt(),
                    readings = readings,
                    amalgamationSubjectIds = amalgamationSubjectIds,
                    componentSubjectIds = componentSubjectIds,
                    unlocked = unlockedAt != null,
                    visuallySimilarSubjectIds = visuallySimilarSubjectIds,
                    meaningHint = meaningHint,
                    readingMnemonic = readingMnemonic,
                    readingHint = readingHint,
                ),
                reviewStatistics = ReviewStatistics(
                    id = reviewStatisticsId?.toInt() ?: -1,
                    meaningCorrect = meaningCorrect?.toInt() ?: 0,
                    meaningIncorrect = meaningIncorrect?.toInt() ?: 0,
                    meaningMaxStreak = meaningMaxStreak?.toInt() ?: 0,
                    meaningCurrentStreak = meaningCurrentSteak?.toInt() ?: 0,
                    readingCorrect = readingCorrect?.toInt() ?: 0,
                    readingIncorrect = readingIncorrect?.toInt() ?: 0,
                    readingMaxStreak = readingMaxStreak?.toInt() ?: 0,
                    readingCurrentStreak = readingCurrentStreak?.toInt() ?: 0,
                    percentageCorrect = percentageCorrect?.toInt() ?: 0,
                    hidden = hidden == 1L
                ),
                assignment = Assignment(
                    id = assignmentId?.toInt() ?: -1,
                    srsStage = srsStage?.toInt() ?: 0,
                    unlockedAt = unlockedAt,
                    startedAt = startedAt,
                    passedAt = passedAt,
                    burnedAt = burnedAt,
                    availableAt = availableAt,
                    resurrectedAt = resurrectedAt,
                    hidden = hidden == 1L,
                ),
                studyMaterials = StudyMaterials(
                    id = studyMaterialsId?.toInt() ?: 0,
                    subjectId = id.toInt(),
                    meaningNote = meaningNote ?: "",
                    readingNote = readingNote ?: "",
                    meaningSynonyms = meaningSynonyms ?: listOf(),
                ),
            )
        }
        else -> {
            ReviewSubject(
                subject = Vocab(
                    id = id.toInt(),
                    level = level.toInt(),
                    characters = characters,
                    meanings = meanings,
                    auxiliaryMeanings = auxiliaryMeanings,
                    meaningMnemonic = meaningMnemonic,
                    lessonPosition = lessonPosition.toInt(),
                    srsSystem = srsSystem.toInt(),
                    readings = readings,
                    componentSubjectIds = componentSubjectIds,
                    unlocked = unlockedAt != null,
                    readingMnemonic = readingMnemonic,
                    partsOfSpeech = partsOfSpeech,
                    contextSentences = contextSentences,
                    pronunciationAudios = pronunciationAudios,
                ),
                reviewStatistics = ReviewStatistics(
                    id = reviewStatisticsId?.toInt() ?: -1,
                    meaningCorrect = meaningCorrect?.toInt() ?: 0,
                    meaningIncorrect = meaningIncorrect?.toInt() ?: 0,
                    meaningMaxStreak = meaningMaxStreak?.toInt() ?: 0,
                    meaningCurrentStreak = meaningCurrentSteak?.toInt() ?: 0,
                    readingCorrect = readingCorrect?.toInt() ?: 0,
                    readingIncorrect = readingIncorrect?.toInt() ?: 0,
                    readingMaxStreak = readingMaxStreak?.toInt() ?: 0,
                    readingCurrentStreak = readingCurrentStreak?.toInt() ?: 0,
                    percentageCorrect = percentageCorrect?.toInt() ?: 0,
                    hidden = hidden == 1L
                ),
                assignment = Assignment(
                    id = assignmentId?.toInt() ?: -1,
                    srsStage = srsStage?.toInt() ?: 0,
                    unlockedAt = unlockedAt,
                    startedAt = startedAt,
                    passedAt = passedAt,
                    burnedAt = burnedAt,
                    availableAt = availableAt,
                    resurrectedAt = resurrectedAt,
                    hidden = hidden == 1L,
                ),
                studyMaterials = StudyMaterials(
                    id = studyMaterialsId?.toInt() ?: 0,
                    subjectId = id.toInt(),
                    meaningNote = meaningNote ?: "",
                    readingNote = readingNote ?: "",
                    meaningSynonyms = meaningSynonyms ?: listOf(),
                ),
            )
        }
    }
}

