package com.gavinferiancek.core_network.subjects_endpoint.model

import com.gavinferiancek.core_domain.subject.Reading
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReadingDto(
    @SerialName("type")
    val type: String? = "",
    @SerialName("primary")
    val primary: Boolean,
    @SerialName("reading")
    val reading: String,
    @SerialName("accepted_answer")
    val acceptedAnswer: Boolean
)

fun ReadingDto.toReading(): Reading {
    return Reading(
        type = type?: "",
        primary = primary,
        reading = reading,
        acceptedAnswer = acceptedAnswer
    )
}

fun List<ReadingDto>.toReadingList(): List<Reading> {
    return map { it.toReading() }
}