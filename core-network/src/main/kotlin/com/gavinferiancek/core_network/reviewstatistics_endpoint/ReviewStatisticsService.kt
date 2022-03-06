package com.gavinferiancek.core_network.reviewstatistics_endpoint

import com.gavinferiancek.core_network.reviewstatistics_endpoint.model.ReviewStatisticsDtoWrapper
import com.gavinferiancek.core_network.reviewstatistics_endpoint.model.ReviewStatisticsResponse
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*

interface ReviewStatisticsService {

    suspend fun getReviewStatistics(
        apiKey: String,
        url: String,
    ): ReviewStatisticsResponse

    suspend fun getReviewStatisticsById(
        apiKey: String,
        id: Int,
    ): ReviewStatisticsDtoWrapper

    companion object Factory {
        fun build(): ReviewStatisticsService {
            return ReviewStatisticsServiceImpl(
                httpClient = HttpClient(Android) {
                    install(JsonFeature) {
                        serializer = KotlinxSerializer(
                            kotlinx.serialization.json.Json {
                                ignoreUnknownKeys = true
                            }
                        )
                    }
                }
            )
        }
    }
}