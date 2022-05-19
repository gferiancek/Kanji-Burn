package com.gavinferiancek.core_network.studymaterials_endpoint.model

import com.gavinferiancek.corecache.cache.StudyMaterialsEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudyMaterialsDtoWrapper(
    @SerialName("data")
    val data: StudyMaterialsDto,
)

fun StudyMaterialsDtoWrapper.toStudyMaterialsEntity(): StudyMaterialsEntity {
    val meaningSynonymsString = when(data.meaningSynonyms.count()) {
        0 -> ""
        1 -> data.meaningSynonyms[0]
        else -> data.meaningSynonyms.joinToString(", ")
    }

    return StudyMaterialsEntity(
        subjectId = data.subjectId.toLong(),
        subjectType = data.subjectType,
        meaningNote = data.meaningNote,
        readingNote = data.readingNote,
        meaningSynonyms = meaningSynonymsString,
    )
}

fun List<StudyMaterialsDtoWrapper>.toStudyMaterialsEntityList(): List<StudyMaterialsEntity> {
    return map { it.toStudyMaterialsEntity() }
}