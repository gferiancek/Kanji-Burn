package com.gavinferiancek.subject_datasource.network.model

import com.gavinferiancek.subject_domain.AuxiliaryMeaning
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuxiliaryMeaningDto(
    @SerialName("type")
    val type: String,
    @SerialName("meaning")
    val meaning: String
)

fun AuxiliaryMeaningDto.toAuxiliaryMeaning(): AuxiliaryMeaning {
    return AuxiliaryMeaning(
        type = type,
        meaning = meaning
    )
}

fun List<AuxiliaryMeaningDto>.toAuxiliaryMeaningList(): List<AuxiliaryMeaning> {
    return map { it.toAuxiliaryMeaning() }
}
