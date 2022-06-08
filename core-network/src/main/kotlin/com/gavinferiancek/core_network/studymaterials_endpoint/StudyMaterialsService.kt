package com.gavinferiancek.core_network.studymaterials_endpoint

import com.gavinferiancek.core_domain.studymaterials.StudyMaterials
import com.gavinferiancek.core_network.studymaterials_endpoint.model.StudyMaterialsDtoWrapper
import com.gavinferiancek.core_network.studymaterials_endpoint.model.StudyMaterialsPostDtoWrapper
import com.gavinferiancek.core_network.studymaterials_endpoint.model.StudyMaterialsResponse
import com.gavinferiancek.corecache.cache.StudyMaterialsEntity
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*

interface StudyMaterialsService {

    suspend fun getStudyMaterials(
        url: String,
    ): StudyMaterialsResponse

    suspend fun getStudyMaterialsById(
        id: Int,
    ): StudyMaterialsDtoWrapper

    suspend fun createStudyMaterials(
        studyMaterials: StudyMaterials,
    ): StudyMaterialsEntity

    suspend fun updateStudyMaterials(
        studyMaterials: StudyMaterials,
    ): StudyMaterialsEntity

    companion object Factory {
        fun build(): StudyMaterialsService {
            return StudyMaterialsServiceImpl(
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