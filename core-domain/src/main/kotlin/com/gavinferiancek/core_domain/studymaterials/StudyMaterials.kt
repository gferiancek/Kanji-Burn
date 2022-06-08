package com.gavinferiancek.core_domain.studymaterials

data class StudyMaterials(
    val id: Int,
    val subjectId: Int,
    val meaningNote: String = "",
    val readingNote: String = "",
    val meaningSynonyms: List<String> = listOf(),
)
