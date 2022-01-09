package com.gavinferiancek.subject_domain

data class Reading(
    val type: String?,
    val primary: Boolean,
    val reading: String,
    val acceptedAnswer: Boolean
)
