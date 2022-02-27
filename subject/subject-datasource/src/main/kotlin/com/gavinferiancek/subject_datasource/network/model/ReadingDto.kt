package com.gavinferiancek.subject_datasource.network.model

import com.gavinferiancek.subject_domain.Reading
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
        type = type,
        primary = primary,
        reading = reading,
        acceptedAnswer = acceptedAnswer
    )
}

fun List<ReadingDto>.toReadingList(): List<Reading> {
    return map { it.toReading() }
}