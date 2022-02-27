package com.gavinferiancek.subject_datasource.network.model

import com.gavinferiancek.subject_domain.PronunciationAudio
import com.gavinferiancek.subject_domain.PronunciationMetaData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PronunciationAudioDto(
    @SerialName("url")
    val url: String,
    @SerialName("metadata")
    val metadata: PronunciationMetaDataDto,
    @SerialName("content_type")
    val contentType: String
)

@Serializable
data class PronunciationMetaDataDto(
    @SerialName("gender")
    val gender: String,
    @SerialName("voice_actor_name")
    val name: String,
    @SerialName("voice_description")
    val description: String,
)

fun PronunciationAudioDto.toPronunciationAudio(): PronunciationAudio {
    return PronunciationAudio(
        url = url,
        metadata = metadata.toPronunciationMetaData()
    )
}

fun PronunciationMetaDataDto.toPronunciationMetaData(): PronunciationMetaData {
    return PronunciationMetaData(
        gender = gender,
        name = name,
        description = description
    )
}
fun List<PronunciationAudioDto>.toPronunciationAudioList() = map { it.toPronunciationAudio() }
