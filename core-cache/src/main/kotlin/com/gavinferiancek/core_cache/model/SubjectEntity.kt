package com.gavinferiancek.core_cache.model

import com.gavinferiancek.corecache.cache.SubjectEntity
import com.gavinferiancek.core_domain.subject.Subject

fun SubjectEntity.toSubject(): Subject {
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
                characterImage = characterImage
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
                contextSentences = contextSentences,
                pronunciationAudios = pronunciationAudios,
            )
        }
    }
}

fun List<SubjectEntity>.toSubjectList(): List<Subject> {
    return map { it.toSubject() }
}