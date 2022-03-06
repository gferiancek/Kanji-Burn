package com.gavinferiancek.core_network.subjects_endpoint.model

import com.gavinferiancek.core_domain.subject.Meaning
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MeaningDto(
    @SerialName("meaning")
    val meaning: String,
    @SerialName("primary")
    val primary: Boolean,
    @SerialName("accepted_answer")
    val acceptedAnswer: Boolean
)

fun MeaningDto.toMeaning(): Meaning {
    return Meaning(
        meaning = meaning,
        primary = primary,
        acceptedAnswer = acceptedAnswer
    )
}

fun List<MeaningDto>.toMeaningList(): List<Meaning> {
    return map { it.toMeaning() }
}