package com.gavinferiancek.core_network.subjects_endpoint.model

import com.gavinferiancek.core_domain.subject.PronunciationAudio
import com.gavinferiancek.core_domain.subject.PronunciationMetaData
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
    @SerialName("pronunciation")
    val pronunciation: String,
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
fun List<PronunciationAudioDto>.toPronunciationAudioList(reading: String) : List<PronunciationAudio> {
    val filtered = this.filter { it.metadata.pronunciation == reading && it.contentType == "audio/mpeg" }
    return filtered.map {
        it.toPronunciationAudio()
    }
}
