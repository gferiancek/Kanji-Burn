package com.gavinferiancek.core_network.studymaterials_endpoint

import com.gavinferiancek.core_network.EndPoints
import com.gavinferiancek.core_network.studymaterials_endpoint.model.StudyMaterialsDtoWrapper
import com.gavinferiancek.core_network.studymaterials_endpoint.model.StudyMaterialsResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class StudyMaterialsServiceImpl(
    private val httpClient: HttpClient,
): StudyMaterialsService {
    override suspend fun getStudyMaterials(
        apiKey: String,
        url: String,
    ): StudyMaterialsResponse {
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
        id: Int,
    ): StudyMaterialsDtoWrapper {
        return httpClient.get {
            headers {
                append(
                    name = HttpHeaders.Authorization,
                    value = "Bearer $apiKey",
                )
            }
            url(urlString = "${EndPoints.STUDY_MATERIALS}/$id")
        }
    }

}