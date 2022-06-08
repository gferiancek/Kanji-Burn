package com.gavinferiancek.core_network.studymaterials_endpoint.model

import com.gavinferiancek.corecache.cache.StudyMaterialsEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudyMaterialsDtoWrapper(
    @SerialName("id")
    val studyMaterialsId: Int,
    @SerialName("data_updated_at")
    val lastUpdated: String,
    @SerialName("data")
    val data: StudyMaterialsDto,
)

fun StudyMaterialsDtoWrapper.toStudyMaterialsEntity(): StudyMaterialsEntity {

    return StudyMaterialsEntity(
        studyMaterialsId = studyMaterialsId.toLong(),
        lastUpdated = lastUpdated,
        subjectId = data.subjectId.toLong(),
        subjectType = data.subjectType,
        meaningNote = data.meaningNote?: "",
        readingNote = data.readingNote?: "",
        meaningSynonyms = data.meaningSynonyms,
    )
}

fun List<StudyMaterialsDtoWrapper>.toStudyMaterialsEntityList(): List<StudyMaterialsEntity> {
    return map { it.toStudyMaterialsEntity() }
}