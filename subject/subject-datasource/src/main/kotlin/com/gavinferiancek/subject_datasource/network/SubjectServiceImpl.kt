package com.gavinferiancek.subject_datasource.network

import com.gavinferiancek.subject_datasource.network.model.SubjectResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class SubjectServiceImpl(
    private val httpClient: HttpClient,
): SubjectService {
    override suspend fun getSubjects(
        apiKey: String,
        nextPage: Int?,
    ): SubjectResponse {
        return httpClient.get {
            headers {
                append(
                    name = HttpHeaders.Authorization,
                    value = "Bearer $apiKey")
            }
            url(urlString = EndPoints.SUBJECTS)
            parameter(
                key = "page_after_id",
                value = nextPage,
            )
        }
    }
}