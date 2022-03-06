package com.gavinferiancek.core_network.subjects_endpoint.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RadicalDto(
    @SerialName("level")
    val level: Int,
    @SerialName("characters")
    val characters: String?,
    @SerialName("character_images")
    val characterImages: List<CharacterImagesDto>,
    @SerialName("meanings")
    val meanings: List<MeaningDto>,
    @SerialName("auxiliary_meanings")
    val auxiliaryMeanings: List<AuxiliaryMeaningDto>,
    @SerialName("meaning_mnemonic")
    val meaningMnemonic: String,
    @SerialName("lesson_position")
    val lessonPosition: Int,
    @SerialName("spaced_repetition_system_id")
    val srsSystem: Int,
    @SerialName("amalgamation_subject_ids")
    val amalgamationSubjectIds: List<Int>
)

@Serializable
data class KanjiDto(
    @SerialName("level")
    val level: Int,
    @SerialName("characters")
    val characters: String,
    @SerialName("meanings")
    val meanings: List<MeaningDto>,
    @SerialName("auxiliary_meanings")
    val auxiliaryMeanings: List<AuxiliaryMeaningDto>,
    @SerialName("meaning_mnemonic")
    val meaningMnemonic: String,
    @SerialName("lesson_position")
    val lessonPosition: Int,
    @SerialName("spaced_repetition_system_id")
    val srsSystem: Int,
    @SerialName("readings")
    val readings: List<ReadingDto>,
    @SerialName("amalgamation_subject_ids")
    val amalgamationSubjectIds: List<Int>,
    @SerialName("component_subject_ids")
    val componentSubjectIds: List<Int>,
    @SerialName("visually_similar_subject_ids")
    val visuallySimilarSubjectIds: List<Int>,
    @SerialName("meaning_hint")
    val meaningHint: String?,
    @SerialName("reading_mnemonic")
    val readingMnemonic: String,
    @SerialName("reading_hint")
    val readingHint: String
)

@Serializable
data class VocabDto(
    @SerialName("level")
    val level: Int,
    @SerialName("characters")
    val characters: String,
    @SerialName("meanings")
    val meanings: List<MeaningDto>,
    @SerialName("auxiliary_meanings")
    val auxiliaryMeanings: List<AuxiliaryMeaningDto>,
    @SerialName("meaning_mnemonic")
    val meaningMnemonic: String,
    @SerialName("lesson_position")
    val lessonPosition: Int,
    @SerialName("spaced_repetition_system_id")
    val srsSystem: Int,
    @SerialName("readings")
    val readings: List<ReadingDto>,
    @SerialName("reading_mnemonic")
    val readingMnemonic: String,
    @SerialName("parts_of_speech")
    val partsOfSpeech: List<String>,
    @SerialName("component_subject_ids")
    val componentSubjectIds: List<Int>,
    @SerialName("context_sentences")
    val contextSentences: List<ContextSentenceDto>,
    @SerialName("pronunciation_audios")
    val pronunciationAudios: List<PronunciationAudioDto>
)