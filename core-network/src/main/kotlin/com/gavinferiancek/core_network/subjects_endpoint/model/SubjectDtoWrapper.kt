package com.gavinferiancek.core_network.subjects_endpoint.model

import com.gavinferiancek.corecache.cache.SubjectEntity
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable(with = SubjectDtoWrapperSerializer::class)
sealed class SubjectDtoWrapper {
    @Serializable
    data class RadicalDtoWrapper(
        @SerialName("id")
        val id: Int,
        @SerialName("object")
        val type: String,
        @SerialName("data")
        val data: RadicalDto
    ): SubjectDtoWrapper()

    @Serializable
    data class KanjiDtoWrapper(
        @SerialName("id")
        val id: Int,
        @SerialName("object")
        val type: String,
        @SerialName("data")
        val data: KanjiDto
    ): SubjectDtoWrapper()

    @Serializable
    data class VocabDtoWrapper(
        @SerialName("id")
        val id: Int,
        @SerialName("object")
        val type: String,
        @SerialName("data")
        val data: VocabDto
    ): SubjectDtoWrapper()
}

object SubjectDtoWrapperSerializer : JsonContentPolymorphicSerializer<SubjectDtoWrapper>(
    SubjectDtoWrapper::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out SubjectDtoWrapper> {
        return when (element.jsonObject["object"]?.jsonPrimitive?.content) {
            "radical" -> SubjectDtoWrapper.RadicalDtoWrapper.serializer()
            "kanji" -> SubjectDtoWrapper.KanjiDtoWrapper.serializer()
            "vocabulary" -> SubjectDtoWrapper.VocabDtoWrapper.serializer()
            else -> throw Exception("Unknown Module: key 'type' not found or does not matches any module type")
        }
    }
}

/**
 * We use SQLdelight's Column Adapters to convert our lists of objects to/from json and store as a String
 * in the database.  Because of that, there isn't really a point of making Entity objects for meanings,
 * readings, etc so we just convert to the Domain versions and then go to from json.
 */
fun SubjectDtoWrapper.toSubjectEntity(): SubjectEntity {
    return when(this) {
        is SubjectDtoWrapper.RadicalDtoWrapper -> {
            SubjectEntity(
                id = id.toLong(),
                type = "radical",
                level = data.level.toLong(),
                characters = data.characters?: data.characterImages.toCharacterImageString(),
                meanings = data.meanings.toMeaningList(),
                auxiliaryMeanings = data.auxiliaryMeanings.toAuxiliaryMeaningList(),
                meaningMnemonic = data.meaningMnemonic,
                lessonPosition = data.lessonPosition.toLong(),
                srsSystem = data.srsSystem.toLong(),
                readings = listOf(),
                amalgamationSubjectIds = data.amalgamationSubjectIds,
                // Rest of fields not present in Radical
                meaningHint = "",
                readingMnemonic = "",
                readingHint = "",
                componentSubjectIds = listOf(),
                visuallySimilarSubjectIds = listOf(),
                partsOfSpeech = listOf(),
                contextSentences = listOf(),
                pronunciationAudios = listOf()
            )
        }
        is SubjectDtoWrapper.KanjiDtoWrapper -> {
            SubjectEntity(
                id = id.toLong(),
                type = "kanji",
                level = data.level.toLong(),
                characters = data.characters,
                meanings = data.meanings.toMeaningList(),
                auxiliaryMeanings = data.auxiliaryMeanings.toAuxiliaryMeaningList(),
                meaningMnemonic = data.meaningMnemonic,
                lessonPosition = data.lessonPosition.toLong(),
                srsSystem = data.srsSystem.toLong(),
                readings = data.readings.toReadingList(),
                meaningHint = data.meaningHint ?: "",
                readingMnemonic = data.readingMnemonic,
                readingHint = data.readingHint,
                amalgamationSubjectIds = data.amalgamationSubjectIds,
                componentSubjectIds = data.componentSubjectIds,
                visuallySimilarSubjectIds = data.visuallySimilarSubjectIds,
                // Rest of fields not present in Kanji
                partsOfSpeech = listOf(),
                contextSentences = listOf(),
                pronunciationAudios = listOf(),
            )
        }
        is SubjectDtoWrapper.VocabDtoWrapper -> {
            SubjectEntity(
                id = id.toLong(),
                type = "vocab",
                level = data.level.toLong(),
                characters = data.characters,
                meanings = data.meanings.toMeaningList(),
                auxiliaryMeanings = data.auxiliaryMeanings.toAuxiliaryMeaningList(),
                meaningMnemonic = data.meaningMnemonic,
                lessonPosition = data.lessonPosition.toLong(),
                srsSystem = data.srsSystem.toLong(),
                readings = data.readings.toReadingList(),
                readingMnemonic = data.readingMnemonic,
                componentSubjectIds = data.componentSubjectIds,
                partsOfSpeech = data.partsOfSpeech,
                contextSentences = data.contextSentences.toContextSentenceList(),
                pronunciationAudios = data.pronunciationAudios.toPronunciationAudioList(data.readings.first { it.primary }.reading),
                // Rest of fields not present in Vocab
                amalgamationSubjectIds = listOf(),
                visuallySimilarSubjectIds = listOf(),
                meaningHint = "",
                readingHint = "",
            )
        }
    }
}

fun List<SubjectDtoWrapper>.toSubjectEntityList(): List<SubjectEntity> {
    return map { it.toSubjectEntity() }
}