package com.gavinferiancek.core_domain.subject

data class PronunciationAudio(
    val url: String,
    val metadata: PronunciationMetaData
)

data class PronunciationMetaData(
    val gender: String,
    val name: String,
    val description: String,
)