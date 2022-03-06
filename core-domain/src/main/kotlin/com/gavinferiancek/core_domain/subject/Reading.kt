package com.gavinferiancek.core_domain.subject

data class Reading(
    val type: String?,
    val primary: Boolean,
    val reading: String,
    val acceptedAnswer: Boolean
)
