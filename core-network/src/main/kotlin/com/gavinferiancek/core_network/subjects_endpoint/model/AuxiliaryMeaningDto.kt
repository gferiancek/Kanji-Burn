package com.gavinferiancek.core_network.subjects_endpoint.model

import com.gavinferiancek.core_domain.subject.AuxiliaryMeaning
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
