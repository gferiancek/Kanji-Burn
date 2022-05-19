package com.gavinferiancek.core_network.studymaterials_endpoint

import com.gavinferiancek.core_network.studymaterials_endpoint.model.StudyMaterialsDtoWrapper
import com.gavinferiancek.core_network.studymaterials_endpoint.model.StudyMaterialsResponse
import com.gavinferiancek.core_network.subjects_endpoint.SubjectsServiceImpl
import com.gavinferiancek.core_network.subjects_endpoint.model.SubjectDtoWrapper
import com.gavinferiancek.core_network.subjects_endpoint.model.SubjectResponse
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlinx.serialization.json.Json

interface StudyMaterialsService {

    suspend fun getStudyMaterials(
        apiKey: String,
        url: String,
    ): StudyMaterialsResponse

    suspend fun getSubjectById(
        apiKey: String,
        id: Int,
    ): StudyMaterialsDtoWrapper

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