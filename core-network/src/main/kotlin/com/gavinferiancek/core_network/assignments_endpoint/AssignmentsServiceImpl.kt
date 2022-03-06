package com.gavinferiancek.core_network.assignments_endpoint

import com.gavinferiancek.core_network.EndPoints
import com.gavinferiancek.core_network.assignments_endpoint.model.AssignmentDtoWrapper
import com.gavinferiancek.core_network.assignments_endpoint.model.AssignmentsResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class AssignmentsServiceImpl(
    private val httpClient: HttpClient,
): AssignmentsService {

    override suspend fun getAssignments(
        apiKey: String,
        url: String,
    ): AssignmentsResponse {
        return httpClient.get {
            headers {
                append(
                    name = HttpHeaders.Authorization,
                    value = "Bearer $apiKey")
            }
            url(urlString = url)
        }
    }

    override suspend fun getAssignmentById(
        apiKey: String,
        id: Int
    ): AssignmentDtoWrapper {
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