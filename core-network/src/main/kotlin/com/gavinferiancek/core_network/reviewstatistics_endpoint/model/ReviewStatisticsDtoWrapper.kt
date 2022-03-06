package com.gavinferiancek.core_network.reviewstatistics_endpoint.model

import com.gavinferiancek.corecache.cache.ReviewStatisticsEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewStatisticsDtoWrapper(
    @SerialName("data_updated_at")
    val lastUpdated: String,
    @SerialName("data")
    val data: ReviewStatisticsDto,
)

fun ReviewStatisticsDtoWrapper.toReviewStatisticsEntity(): ReviewStatisticsEntity {
    return ReviewStatisticsEntity(
        lastUpdated = lastUpdated,
        subjectId = data.subjectId.toLong(),
        subjectType = data.subjectType,
        meaningCorrect = data.meaningCorrect.toLong(),
        meaningIncorrect = data.meaningIncorrect.toLong(),
        meaningMaxStreak = data.meaningMaxStreak.toLong(),
        meaningCurrentSteak = data.meaningCurrentStreak.toLong(),
        readingCorrect = data.readingCorrect.toLong(),
        readingIncorrect = data.readingIncorrect.toLong(),
        readingMaxStreak = data.readingMaxStreak.toLong(),
        readingCurrentStreak = data.readingCurrentStreak.toLong(),
        percentageCorrect = data.percentageCorrect.toLong(),
        hidden = if (data.hidden) 1L else 0L
    )
}

fun List<ReviewStatisticsDtoWrapper>.toReviewStatisticsEntityList(): List<ReviewStatisticsEntity> {
    return map { it.toReviewStatisticsEntity() }
}