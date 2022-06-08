package com.gavinferiancek.core_network.studymaterials_endpoint.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudyMaterialsPostDto(
    @SerialName("subject_id")
    val subjectId: Int,
    @SerialName("meaning_note")
    val meaningNote: String,
    @SerialName("reading_note")
    val readingNote: String,
    @SerialName("meaning_synonyms")
    val meaningSynonyms: List<String>,
)
