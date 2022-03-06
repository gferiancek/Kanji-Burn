package com.gavinferiancek.core_network.assignments_endpoint

import com.gavinferiancek.core_network.EndPoints
import com.gavinferiancek.core_network.assignments_endpoint.model.AssignmentDtoWrapper
import com.gavinferiancek.core_network.assignments_endpoint.model.AssignmentsResponse
import com.gavinferiancek.core_network.subjects_endpoint.SubjectsService
import com.gavinferiancek.core_network.subjects_endpoint.SubjectsServiceImpl
import com.gavinferiancek.core_network.subjects_endpoint.model.SubjectDtoWrapper
import com.gavinferiancek.core_network.subjects_endpoint.model.SubjectResponse
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

interface AssignmentsService {

    suspend fun getAssignments(
        apiKey: String,
        url: String,
    ): AssignmentsResponse

    suspend fun getAssignmentById(
        apiKey: String,
        id: Int,
    ): AssignmentDtoWrapper

    companion object Factory {
        fun build(): AssignmentsService {
            return AssignmentsServiceImpl(
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