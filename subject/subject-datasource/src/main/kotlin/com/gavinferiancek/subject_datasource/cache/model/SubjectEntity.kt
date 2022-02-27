package com.gavinferiancek.subject_datasource.cache.model

import com.gavinferiancek.subject_domain.Kanji
import com.gavinferiancek.subject_domain.Radical
import com.gavinferiancek.subject_domain.Subject
import com.gavinferiancek.subject_domain.Vocab
import com.gavinferiancek.subjectdatasource.cache.SubjectEntity

// amalgationIds and other List<Int> can be stored in DB as strings with list.joinToString(separator = ",").
// To convert back use amalString.removeSurrounding("[","]").split(",").map{ it.toInt() }

fun SubjectEntity.toSubject(): Subject {
    return when(type) {
        "radical" -> {
            Radical(
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
                characterImage = characterImage
            )
        }
        "kanji" -> {
            Kanji(
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
            Vocab(
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