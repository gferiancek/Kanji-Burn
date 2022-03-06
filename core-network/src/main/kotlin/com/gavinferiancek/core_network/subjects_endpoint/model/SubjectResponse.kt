package com.gavinferiancek.core_network.subjects_endpoint.model

import com.gavinferiancek.core_network.PageData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubjectResponse(
    @SerialName("pages")
    val pageData: PageData,
    @SerialName("data_updated_at")
    val lastUpdated: String,
    @SerialName("data")
    val data: List<SubjectDtoWrapper>
)