package com.gavinferiancek.core_domain.subject

import java.util.*


sealed class Subject {
    abstract val id: Int
    abstract val level: Int
    abstract val characters: String
    abstract val meanings: List<Meaning>
    abstract val auxiliaryMeanings: List<AuxiliaryMeaning>
    abstract val meaningMnemonic: String
    abstract val lessonPosition: Int
    abstract val srsSystem: Int
    abstract val unlocked: Boolean
    // The following fields are shared by two of the three subclasses. In order to prevent excessive
    // type checking, we still add it to the superclass and provide and empty value in the subclass
    // that doesn't require the field.
    abstract val readings: List<Reading>
    abstract val amalgamationSubjectIds: List<Int>
    abstract val componentSubjectIds: List<Int>
    abstract val readingMnemonic: String
}

data class Radical(
    override val id: Int,
    override val level: Int,
    override val characters: String,
    override val meanings: List<Meaning>,
    override val auxiliaryMeanings: List<AuxiliaryMeaning>,
    override val meaningMnemonic: String,
    override val lessonPosition: Int,
    override val srsSystem: Int,
    override val unlocked: Boolean,
    override val readings: List<Reading> = listOf(),
    override val amalgamationSubjectIds: List<Int>,
    override val componentSubjectIds: List<Int> = listOf(),
    override val readingMnemonic: String = "",
) : Subject()

data class Kanji(
    override val id: Int,
    override val level: Int,
    override val characters: String,
    override val meanings: List<Meaning>,
    override val auxiliaryMeanings: List<AuxiliaryMeaning>,
    override val meaningMnemonic: String,
    override val lessonPosition: Int,
    override val srsSystem: Int,
    override val unlocked: Boolean,
    override val readings: List<Reading>,
    override val amalgamationSubjectIds: List<Int>,
    override val componentSubjectIds: List<Int>,
    override val readingMnemonic: String,
    val visuallySimilarSubjectIds: List<Int>,
    val meaningHint: String,
    val readingHint: String
) : Subject()

data class Vocab(
    override val id: Int,
    override val level: Int,
    override val characters: String,
    override val meanings: List<Meaning>,
    override val auxiliaryMeanings: List<AuxiliaryMeaning>,
    override val meaningMnemonic: String,
    override val lessonPosition: Int,
    override val srsSystem: Int,
    override val unlocked: Boolean,
    override val readings: List<Reading>,
    override val amalgamationSubjectIds: List<Int> = listOf(),
    override val componentSubjectIds: List<Int>,
    override val readingMnemonic: String,
    val partsOfSpeech: List<String>,
    val contextSentences: List<ContextSentence>,
    val pronunciationAudios: List<PronunciationAudio>
) : Subject() {
    fun buildPartsOfSpeechString(): String {
        return partsOfSpeech.joinToString(", ") { partOfSpeech ->
            partOfSpeech.lowercase(Locale.getDefault()).replaceFirstChar { char ->
                char.titlecase(Locale.getDefault())
            }
        }
    }
}
