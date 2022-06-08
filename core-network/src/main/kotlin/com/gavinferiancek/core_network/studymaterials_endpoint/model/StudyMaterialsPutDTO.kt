package com.gavinferiancek.core_network.studymaterials_endpoint.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudyMaterialsPutDTO(
    @SerialName("meaning_note")
    val meaning_note: String,
    @SerialName("reading_note")
    val reading_note: String,
    @SerialName("meaning_synonyms")
    val meaning_synonyms: List<String>
)
