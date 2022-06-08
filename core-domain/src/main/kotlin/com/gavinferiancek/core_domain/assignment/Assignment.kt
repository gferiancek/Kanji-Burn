package com.gavinferiancek.core_domain.assignment

import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class Assignment(
    val id: Int,
    val srsStage: Int,
    val unlockedAt: String?,
    val startedAt: String?,
    val passedAt: String?,
    val burnedAt: String?,
    val availableAt: String?,
    val resurrectedAt: String?,
    val hidden: Boolean,
) {
    private fun formatDate(dateTime: ZonedDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
        return dateTime.format(formatter)
    }

    fun formatUnlockedAt(): String {
        if (unlockedAt.isNullOrBlank()) return "-"
        return formatDate(ZonedDateTime.parse(unlockedAt))
    }

    fun formatAvailableAt(): String {
        if (availableAt.isNullOrBlank()) return "-"
        val currentTime = ZonedDateTime.now()
        val dateTime = ZonedDateTime.parse(availableAt)
        return if (currentTime.isBefore(dateTime)) {
            formatDate(dateTime)
        } else {
            "Available Now"
        }
    }
}
