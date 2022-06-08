package com.gavinferiancek.core_network.studymaterials_endpoint.model

import com.gavinferiancek.core_domain.studymaterials.StudyMaterials
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudyMaterialsPutDtoWrapper(
    @SerialName("study_material")
    val study_material: StudyMaterialsPutDTO
)

fun StudyMaterials.toStudyMaterialsPutDtoWrapper(): StudyMaterialsPutDtoWrapper {
    return StudyMaterialsPutDtoWrapper(
        study_material = StudyMaterialsPutDTO(
            meaning_note = meaningNote,
            reading_note = readingNote,
            meaning_synonyms = meaningSynonyms,
        )
    )
}

