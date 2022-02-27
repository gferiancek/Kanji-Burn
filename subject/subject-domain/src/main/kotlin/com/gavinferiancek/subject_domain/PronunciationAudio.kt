package com.gavinferiancek.subject_domain

data class PronunciationAudio(
    val url: String,
    val metadata: PronunciationMetaData
)

data class PronunciationMetaData(
    val gender: String,
    val name: String,
    val description: String,
)