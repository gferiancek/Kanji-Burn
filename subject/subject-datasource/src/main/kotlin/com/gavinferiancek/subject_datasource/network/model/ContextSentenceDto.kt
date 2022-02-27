package com.gavinferiancek.subject_datasource.network.model


import com.gavinferiancek.subject_domain.ContextSentence
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContextSentenceDto(
    @SerialName("en")
    val en: String,
    @SerialName("ja")
    val jp: String
)

fun ContextSentenceDto.toContextSentence(): ContextSentence {
    return ContextSentence(
        en = en,
        jp = jp
    )
}

fun List<ContextSentenceDto>.toContextSentenceList(): List<ContextSentence> {
    return map { it.toContextSentence() }
}