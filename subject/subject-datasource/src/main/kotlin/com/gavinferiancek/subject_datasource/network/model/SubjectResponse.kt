package com.gavinferiancek.subject_datasource.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubjectResponse(
    @SerialName("pages")
    val pageData: PageData,
    @SerialName("data")
    val data: List<SubjectDtoWrapper>
)