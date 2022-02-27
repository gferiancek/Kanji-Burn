package com.gavinferiancek.subject_domain


abstract class Subject(
    open val id: Int,
    open val level: Int,
    open val characters: String,
    open val meanings: List<Meaning>,
    open val auxiliaryMeanings: List<AuxiliaryMeaning>,
    open val meaningMnemonic: String,
    open val lessonPosition: Int,
    open val srsSystem: Int,
    open val readings: List<Reading>,
) {
    /**
     * API is structured in a way that the first meaning is always the primary, but we use List.first()
     * as a failsafe in case the API for some reason returned data that had primary set to false as the first item.
     */
    fun getPrimaryMeaning() = meanings.first { it.primary }.meaning

    abstract fun getPrimaryReading(): String

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
    override val readings: List<Reading> = listOf(),
    val amalgamationSubjectIds: List<Int>,
    val characterImage: String
) : Subject(id, level, characters, meanings, auxiliaryMeanings, meaningMnemonic, lessonPosition, srsSystem, readings) {

    /**
     * Radicals do not have have a reading, so we return an empty string.  This function is included in the Base Subject class
     * instead of implemented in just the Kanji/Vocab class to allow us to call the method when we aren't 100% certain
     * of the type of Subject we're working with. (Main use is the SubjectFilterOrderUseCase where we'll dealing with
     * multiple lists of subjects, but the compiler doesn't know that the particular list is a list of Kanji/Vocab, thus locking
     * us out of the getPrimaryReading() function.)
     */
    override fun getPrimaryReading(): String = ""
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
) : Subject(id, level, characters, meanings, auxiliaryMeanings, meaningMnemonic, lessonPosition, srsSystem, readings) {

    override fun getPrimaryReading() = readings.first() { it.primary }.reading
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
    val componentSubjectIds: List<Int>,
    val contextSentences: List<ContextSentence>,
    val pronunciationAudios: List<PronunciationAudio>
) : Subject(id, level, characters, meanings, auxiliaryMeanings, meaningMnemonic, lessonPosition, srsSystem, readings) {

    override fun getPrimaryReading() = readings.first() { it.primary }.reading
}