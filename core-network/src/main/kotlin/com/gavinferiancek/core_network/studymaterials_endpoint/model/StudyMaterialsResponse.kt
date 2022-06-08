package com.gavinferiancek.core_network.studymaterials_endpoint.model

import com.gavinferiancek.core_network.PageData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudyMaterialsResponse(
    @SerialName("pages")
    val pageData: PageData,
    @SerialName("data_updated_at")
    val lastUpdated: String,
    @SerialName("data")
    val data: List<StudyMaterialsDtoWrapper>,
)
