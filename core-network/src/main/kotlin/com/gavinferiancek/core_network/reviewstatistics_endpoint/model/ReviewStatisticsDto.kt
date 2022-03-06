package com.gavinferiancek.core_network.reviewstatistics_endpoint.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewStatisticsDto(
    @SerialName("subject_id")
    val subjectId: Int,
    @SerialName("subject_type")
    val subjectType: String,
    @SerialName("meaning_correct")
    val meaningCorrect: Int,
    @SerialName("meaning_incorrect")
    val meaningIncorrect: Int,
    @SerialName("meaning_max_streak")
    val meaningMaxStreak: Int,
    @SerialName("meaning_current_streak")
    val meaningCurrentStreak: Int,
    @SerialName("reading_correct")
    val readingCorrect: Int,
    @SerialName("reading_incorrect")
    val readingIncorrect: Int,
    @SerialName("reading_max_streak")
    val readingMaxStreak: Int,
    @SerialName("reading_current_streak")
    val readingCurrentStreak: Int,
    @SerialName("percentage_correct")
    val percentageCorrect: Int,
    @SerialName("hidden")
    val hidden: Boolean,
)
