package com.gavinferiancek.core_network.subjects_endpoint.model


import com.gavinferiancek.core_domain.subject.ContextSentence
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