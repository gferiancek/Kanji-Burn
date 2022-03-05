package com.gavinferiancek.subject_datasource.network

import com.gavinferiancek.subject_datasource.network.model.SubjectDtoWrapper
import com.gavinferiancek.subject_datasource.network.model.SubjectResponse
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*


interface SubjectService {

    suspend fun getSubjects(
        apiKey: String,
        url: String,
    ): SubjectResponse

    suspend fun getSubjectById(
        apiKey: String,
        id: Int,
    ): SubjectDtoWrapper

    companion object Factory {
        fun build(): SubjectService {
            return SubjectServiceImpl(
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