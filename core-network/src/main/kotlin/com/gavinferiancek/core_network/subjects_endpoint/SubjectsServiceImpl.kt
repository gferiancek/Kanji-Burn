package com.gavinferiancek.core_network.subjects_endpoint

import com.gavinferiancek.core_network.subjects_endpoint.model.SubjectDtoWrapper
import com.gavinferiancek.core_network.subjects_endpoint.model.SubjectResponse
import com.gavinferiancek.core_network.EndPoints
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class SubjectsServiceImpl(
    private val httpClient: HttpClient,
): SubjectsService {

    override suspend fun getSubjects(
        apiKey: String,
        url: String,
    ): SubjectResponse {
        return httpClient.get {
            headers {
                append(
                    name = HttpHeaders.Authorization,
                    value = "Bearer $apiKey")
            }
            url(urlString = url)
        }
    }

    override suspend fun getSubjectById(
        apiKey: String,
        id: Int
    ): SubjectDtoWrapper {
        return httpClient.get {
            headers {
                append(
                    name = HttpHeaders.Authorization,
                    value = "Bearer $apiKey",
                )
            }
            url(urlString = "${EndPoints.SUBJECTS}/$id")
        }
    }
}