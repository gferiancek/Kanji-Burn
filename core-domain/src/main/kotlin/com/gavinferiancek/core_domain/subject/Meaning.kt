package com.gavinferiancek.core_domain.subject

data class Meaning(
    val meaning: String,
    val primary: Boolean,
    val acceptedAnswer: Boolean
)