package com.gavinferiancek.core_cache.model

import com.gavinferiancek.corecache.cache.SubjectEntity
import com.gavinferiancek.core_domain.subject.Subject
import com.gavinferiancek.corecache.cache.ListSubjectEntity

fun ListSubjectEntity.toSubject(): Subject {
    return when(type) {
        "radical" -> {
            Subject.Radical(
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
            )
        }
        "kanji" -> {
            Subject.Kanji(
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
            )
        }
        else -> {
            Subject.Vocab(
                id = id.toInt(),
                level = level.toInt(),
                characters = characters,
                meanings = meanings,
                auxiliaryMeanings = auxiliaryMeanings,
                meaningMnemonic = meaningMnemonic,
                lessonPosition = lessonPosition.toInt(),
                srsSystem = srsSystem.toInt(),
                readings = readings,
                readingMnemonic = readingMnemonic,
                partsOfSpeech = partsOfSpeech,
                componentSubjectIds = componentSubjectIds,
                unlocked = unlockedAt != null,
                contextSentences = contextSentences,
                pronunciationAudios = pronunciationAudios,
            )
        }
    }
}

fun List<ListSubjectEntity>.toSubjectList(): List<Subject> {
    return map { it.toSubject() }
}