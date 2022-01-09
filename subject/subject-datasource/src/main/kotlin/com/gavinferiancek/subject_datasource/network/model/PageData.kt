package com.gavinferiancek.subject_datasource.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PageData(
    @SerialName("per_page")
    val pageSize: Int,
    @SerialName("next_url")
    val nextUrl: String?,
)