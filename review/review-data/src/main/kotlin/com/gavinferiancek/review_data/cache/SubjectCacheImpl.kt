package com.gavinferiancek.review_data.cache

import com.gavinferiancek.core_cache.model.toSubjectList
import com.gavinferiancek.core_domain.assignment.Assignment
import com.gavinferiancek.core_domain.reviewstatistics.ReviewStatistics
import com.gavinferiancek.core_domain.subject.Subject
import com.gavinferiancek.corecache.cache.ReviewSubjectEntity
import com.gavinferiancek.corecache.cache.SubjectEntityQueries
import com.gavinferiancek.review_domain.model.ReviewSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SubjectCacheImpl(
    private val queries: SubjectEntityQueries,
) : SubjectCache {

    override suspend fun getAllSubjects(): List<Subject> {
        var subjects: List<Subject>
        withContext(Dispatchers.IO) {
            subjects = queries.getAllListSubjects().executeAsList().toSubjectList()
        }
        return subjects
    }

    override suspend fun getReviewSubjectById(id: Long): ReviewSubject {
        return queries.getReviewSubjectById(id).executeAsOne().toReviewSubject()
    }

    override suspend fun getSubjectsById(ids: List<Long>): List<Subject> {
        return queries.getListSubjectsById(ids).executeAsList().toSubjectList()
    }
}

fun ReviewSubjectEntity.toReviewSubject(): ReviewSubject {
    return when (type) {
        "radical" -> {
            ReviewSubject(
                subject = Subject.Radical(
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
                    srsStage = srsStage?.toInt() ?: 0,
                    unlockedAt = unlockedAt,
                    startedAt = startedAt,
                    passedAt = passedAt,
                    burnedAt = burnedAt,
                    availableAt = availableAt,
                    resurrectedAt = resurrectedAt,
                    hidden = hidden == 1L,
                ),
            )
        }
        "kanji" -> {
            ReviewSubject(
                subject = Subject.Kanji(
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
                    srsStage = srsStage?.toInt() ?: 0,
                    unlockedAt = unlockedAt,
                    startedAt = startedAt,
                    passedAt = passedAt,
                    burnedAt = burnedAt,
                    availableAt = availableAt,
                    resurrectedAt = resurrectedAt,
                    hidden = hidden == 1L,
                )
            )
        }
        else -> {
            ReviewSubject(
                subject = Subject.Vocab(
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
                    srsStage = srsStage?.toInt() ?: 0,
                    unlockedAt = unlockedAt,
                    startedAt = startedAt,
                    passedAt = passedAt,
                    burnedAt = burnedAt,
                    availableAt = availableAt,
                    resurrectedAt = resurrectedAt,
                    hidden = hidden == 1L,
                )
            )
        }
    }
}
