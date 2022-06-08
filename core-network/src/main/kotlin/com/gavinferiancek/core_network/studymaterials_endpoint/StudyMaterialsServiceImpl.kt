package com.gavinferiancek.core_network.studymaterials_endpoint

import com.gavinferiancek.core_domain.studymaterials.StudyMaterials
import com.gavinferiancek.core_network.EndPoints
import com.gavinferiancek.core_network.PageData
import com.gavinferiancek.core_network.studymaterials_endpoint.model.*
import com.gavinferiancek.corecache.cache.StudyMaterialsEntity
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class StudyMaterialsServiceImpl(
    private val httpClient: HttpClient,
): StudyMaterialsService {
    private val apiKey = EndPoints.apiKey

    override suspend fun getStudyMaterials(
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

    override suspend fun getStudyMaterialsById(
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

    override suspend fun createStudyMaterials(
        studyMaterials: StudyMaterials,
    ): StudyMaterialsEntity {
        return httpClient.post<StudyMaterialsDtoWrapper> {
            headers {
                append(
                    name = HttpHeaders.Authorization,
                    value = "Bearer $apiKey"
                )
            }
            url(urlString = EndPoints.STUDY_MATERIALS)
            contentType(ContentType.Application.Json)
            body = studyMaterials.toStudyMaterialsPostDtoWrapper()
        }.toStudyMaterialsEntity()
    }

    override suspend fun updateStudyMaterials(
        studyMaterials: StudyMaterials,
    ): StudyMaterialsEntity {
        return httpClient.put<StudyMaterialsDtoWrapper> {
            headers {
                append(
                    name = HttpHeaders.Authorization,
                    value = "Bearer $apiKey"
                )
            }
            url(urlString = "${EndPoints.STUDY_MATERIALS}/${studyMaterials.id}")
            contentType(ContentType.Application.Json)
            body = studyMaterials.toStudyMaterialsPutDtoWrapper()
        }.toStudyMaterialsEntity()
    }
}