package com.gavinferiancek.core_network.studymaterials_endpoint.model

import com.gavinferiancek.core_domain.studymaterials.StudyMaterials
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudyMaterialsPostDtoWrapper(
    @SerialName("study_material")
    val studyMaterial: StudyMaterialsPostDto,
)

fun StudyMaterials.toStudyMaterialsPostDtoWrapper(): StudyMaterialsPostDtoWrapper {
    return StudyMaterialsPostDtoWrapper(
        studyMaterial = StudyMaterialsPostDto(
            subjectId = subjectId,
            meaningNote = meaningNote,
            readingNote = readingNote,
            meaningSynonyms = meaningSynonyms,
        )
    )
}