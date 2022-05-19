package com.gavinferiancek.core_domain.subject

import java.util.*


sealed class Subject(
    open val id: Int,
    open val level: Int,
    open val characters: String,
    open val meanings: List<Meaning>,
    open val auxiliaryMeanings: List<AuxiliaryMeaning>,
    open val meaningMnemonic: String,
    open val lessonPosition: Int,
    open val srsSystem: Int,
    open val componentSubjectIds: List<Int>,
    open val unlocked: Boolean,
    open val readings: List<Reading> = listOf(),
) {
    /**
     * API is structured in a way that the first meaning is always the primary, but we use List.first{ it.primary }
     * as a failsafe in case the API for some reason returned data that had primary set to false as the first item.
     */
    fun getPrimaryMeaning() = meanings.first { it.primary }.meaning

    /**
     * Radicals do not have have a reading, so by leaving this open we can choose not to implement it
     * in the Radical class. Same List.first() logic as above.
     */
    open fun getPrimaryReading() = readings.first { it.primary }.reading

    /**
     * Meanings are stored as a list of Meaning objects and needs to be built into a string before being
     * displayed to the UI. We provide a helper method to find all non-primary meanings and add them to
     * a single, properly formatted string.
     */
    open fun getAlternateMeanings(): String {
        val alternateMeaningString = StringBuilder()
        val alternateMeanings = meanings.filter { !it.primary }
        alternateMeanings.forEachIndexed { index, meaning ->
            if (index == alternateMeanings.count() - 1) {
                alternateMeaningString.append(meaning.meaning)
            } else {
                alternateMeaningString.append("${meaning.meaning}, ")
            }
        }
        return alternateMeaningString.toString()
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
        override val componentSubjectIds: List<Int> = listOf(),
        override val unlocked: Boolean,
        val amalgamationSubjectIds: List<Int>,
    ) : Subject(
        id,
        level,
        characters,
        meanings,
        auxiliaryMeanings,
        meaningMnemonic,
        lessonPosition,
        srsSystem,
        componentSubjectIds,
        unlocked
    )

    data class Kanji(
        override val id: Int,
        override val level: Int,
        override val characters: String,
        override val meanings: List<Meaning>,
        override val auxiliaryMeanings: List<AuxiliaryMeaning>,
        override val meaningMnemonic: String,
        override val lessonPosition: Int,
        override val srsSystem: Int,
        override val readings: List<Reading>,
        val amalgamationSubjectIds: List<Int>,
        override val componentSubjectIds: List<Int>,
        override val unlocked: Boolean,
        val visuallySimilarSubjectIds: List<Int>,
        val meaningHint: String,
        val readingMnemonic: String,
        val readingHint: String
    ) : Subject(
        id,
        level,
        characters,
        meanings,
        auxiliaryMeanings,
        meaningMnemonic,
        lessonPosition,
        srsSystem,
        componentSubjectIds,
        unlocked,
        readings
    ) {

        /**
         * Kanji can have many different readings and also have different types. (On'yomi, Kun'yomi,
         * and Nanori).  This will take a supplied type and build a string out of all readings that
         * match the type.
         */
        fun buildReadingString(type: String): String {
            val readingString = StringBuilder()
            val filteredReadings = readings.filter { it.type == type }
            filteredReadings.forEachIndexed { index, reading ->
                if (index == filteredReadings.count() - 1) {
                    readingString.append(reading.reading)
                } else {
                    readingString.append("${reading.reading}, ")
                }
            }
            return readingString.toString()
        }
    }

    data class Vocab(
        override val id: Int,
        override val level: Int,
        override val characters: String,
        override val meanings: List<Meaning>,
        override val auxiliaryMeanings: List<AuxiliaryMeaning>,
        override val meaningMnemonic: String,
        override val lessonPosition: Int,
        override val srsSystem: Int,
        override val readings: List<Reading>,
        val readingMnemonic: String,
        val partsOfSpeech: List<String>,
        override val componentSubjectIds: List<Int>,
        override val unlocked: Boolean,
        val contextSentences: List<ContextSentence>,
        val pronunciationAudios: List<PronunciationAudio>
    ) : Subject(
        id,
        level,
        characters,
        meanings,
        auxiliaryMeanings,
        meaningMnemonic,
        lessonPosition,
        srsSystem,
        componentSubjectIds,
        unlocked,
        readings
    ) {

        fun buildPartsOfSpeechString(): String {
            return partsOfSpeech.joinToString(", ") { partOfSpeech ->
                partOfSpeech.lowercase(Locale.getDefault()).replaceFirstChar { char ->
                    char.titlecase(Locale.getDefault())
                }
            }
        }
    }
}