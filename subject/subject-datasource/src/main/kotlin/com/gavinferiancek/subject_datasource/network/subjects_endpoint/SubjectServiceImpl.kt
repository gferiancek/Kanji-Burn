package com.gavinferiancek.subject_datasource.network.subjects_endpoint

import com.gavinferiancek.subject_datasource.network.EndPoints
import com.gavinferiancek.subject_datasource.network.subjects_endpoint.model.SubjectDtoWrapper
import com.gavinferiancek.subject_datasource.network.subjects_endpoint.model.SubjectResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class SubjectServiceImpl(
    private val httpClient: HttpClient,
): SubjectService {

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