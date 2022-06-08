package com.gavinferiancek.core_domain.subject


data class Reading(
    val type: String = "",
    val primary: Boolean,
    val reading: String,
    val acceptedAnswer: Boolean = false,
)

fun List<Reading>.getPrimaryReading(): String {
    return first { it.primary }.reading
}

fun List<Reading>.filterReadingsByType(filterType: String): Reading {
    val filteredReadings = filter { it.type == filterType }
    val isPrimary = filteredReadings.any { it.primary }
    val readingString = StringBuilder()

    filteredReadings.forEachIndexed { index, reading ->
        if (index == filteredReadings.count() - 1) {
            readingString.append(reading.reading)
        } else {
            readingString.append("${reading.reading}, ")
        }
    }
    return Reading(
        primary = isPrimary,
        reading = readingString.toString(),
    )
}