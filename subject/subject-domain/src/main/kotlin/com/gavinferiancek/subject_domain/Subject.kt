package com.gavinferiancek.subject_domain


sealed class Subject(
    open val id: Int,
    open val level: Int,
    open val characters: String,
    open val meanings: List<Meaning>,
    open val auxiliaryMeanings: List<AuxiliaryMeaning>,
    open val meaningMnemonic: String,
    open val lessonPosition: Int,
    open val srsSystem: Int,
    open val readings: List<Reading> = listOf(),
) {
    /**
     * API is structured in a way that the first meaning is always the primary, but we use List.first()
     * as a failsafe in case the API for some reason returned data that had primary set to false as the first item.
     */
    fun getPrimaryMeaning() = meanings.first { it.primary }.meaning

    /**
     * Radicals do not have have a reading, so by leaving this open we can choose not to implement it
     * in the Radical class. Same List.first() logic as above.
     */
    open fun getPrimaryReading() = readings.first { it.primary }.reading



    data class Radical(
        override val id: Int,
        override val level: Int,
        override val characters: String,
        override val meanings: List<Meaning>,
        override val auxiliaryMeanings: List<AuxiliaryMeaning>,
        override val meaningMnemonic: String,
        override val lessonPosition: Int,
        override val srsSystem: Int,
        val amalgamationSubjectIds: List<Int>,
        val characterImage: String
    ) : Subject(id, level, characters, meanings, auxiliaryMeanings, meaningMnemonic, lessonPosition, srsSystem) {

        /**
         * Some radicals in the API do not have an ASCII form, and in that case we have to fall back to displaying
         * an image. Because of that, we provide this helper function to always provide the correct String.
         */
        fun getRadicalCharacters() = characters.ifBlank { characterImage }
    }

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
        val componentSubjectIds: List<Int>,
        val visuallySimilarSubjectIds: List<Int>,
        val meaningHint: String?,
        val readingMnemonic: String,
        val readingHint: String
    ) : Subject(id, level, characters, meanings, auxiliaryMeanings, meaningMnemonic, lessonPosition, srsSystem, readings)

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
        val componentSubjectIds: List<Int>,
        val contextSentences: List<ContextSentence>,
        val pronunciationAudios: List<PronunciationAudio>
    ) : Subject(id, level, characters, meanings, auxiliaryMeanings, meaningMnemonic, lessonPosition, srsSystem, readings)
}