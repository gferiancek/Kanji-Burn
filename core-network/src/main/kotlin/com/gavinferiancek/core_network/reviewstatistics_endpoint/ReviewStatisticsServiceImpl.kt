package com.gavinferiancek.core_network.reviewstatistics_endpoint

import com.gavinferiancek.core_network.EndPoints
import com.gavinferiancek.core_network.assignments_endpoint.AssignmentsService
import com.gavinferiancek.core_network.assignments_endpoint.model.AssignmentDtoWrapper
import com.gavinferiancek.core_network.assignments_endpoint.model.AssignmentsResponse
import com.gavinferiancek.core_network.reviewstatistics_endpoint.model.ReviewStatisticsDtoWrapper
import com.gavinferiancek.core_network.reviewstatistics_endpoint.model.ReviewStatisticsResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class ReviewStatisticsServiceImpl(
    private val httpClient: HttpClient,
): ReviewStatisticsService {

    override suspend fun getReviewStatistics(
        apiKey: String,
        url: String,
    ): ReviewStatisticsResponse {
        return httpClient.get {
            headers {
                append(
                    name = HttpHeaders.Authorization,
                    value = "Bearer $apiKey")
            }
            url(urlString = url)
        }
    }

    override suspend fun getReviewStatisticsById(
        apiKey: String,
        id: Int
    ): ReviewStatisticsDtoWrapper {
        return httpClient.get {
            headers {
                append(
                    name = HttpHeaders.Authorization,
                    value = "Bearer $apiKey",
                )
            }
            url(urlString = "${EndPoints.ASSIGNMENTS}/$id")
        }
    }
}