package com.gavinferiancek.core_network.subjects_endpoint

import com.gavinferiancek.core_network.subjects_endpoint.model.SubjectDtoWrapper
import com.gavinferiancek.core_network.subjects_endpoint.model.SubjectResponse
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*


interface SubjectsService {

    suspend fun getSubjects(
        apiKey: String,
        url: String,
    ): SubjectResponse

    suspend fun getSubjectById(
        apiKey: String,
        id: Int,
    ): SubjectDtoWrapper

    companion object Factory {
        fun build(): SubjectsService {
            return SubjectsServiceImpl(
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