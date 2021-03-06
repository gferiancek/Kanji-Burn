package com.gavinferiancek.subject_datasource.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterImagesDto(
    @SerialName("url")
    val url: String,
    @SerialName("metadata")
    val metadata: CharacterImagesMetadataDto,
    @SerialName("content_type")
    val contentType: String
)

@Serializable
data class CharacterImagesMetadataDto(
    @SerialName("style_name")
    val styleName: String? = ""
)

fun List<CharacterImagesDto>.toCharacterImageString(): String {
    val matchingImage = this.find { it.metadata.styleName == "256px" }
    return matchingImage?.url ?: ""
}