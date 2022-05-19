package com.gavinferiancek.core_network.subjects_endpoint.model

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
    val matchingImage = this.find { it.metadata.styleName == "128px" }
    return matchingImage?.url ?: ""
}