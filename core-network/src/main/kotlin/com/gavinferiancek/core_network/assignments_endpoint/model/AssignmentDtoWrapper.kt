package com.gavinferiancek.core_network.assignments_endpoint.model

import com.gavinferiancek.corecache.cache.AssignmentEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssignmentDtoWrapper(
    @SerialName("data_updated_at")
    val lastUpdated: String,
    @SerialName("data")
    val data: AssignmentDto,
)

fun AssignmentDtoWrapper.toAssignmentEntity(): AssignmentEntity {
    return AssignmentEntity(
        subjectId = data.subjectId.toLong(),
        lastUpdated = lastUpdated,
        subjectType = data.subjectType,
        srsStage = data.srsStage.toLong(),
        unlockedAt = data.unlockedAt,
        startedAt = data.startedAt,
        passedAt = data.passedAt,
        burnedAt = data.burnedAt,
        availableAt = data.availableAt,
        resurrectedAt = data.resurrectedAt,
        hidden = if (data.hidden) 1L else 0L
    )
}

fun List<AssignmentDtoWrapper>.toAssignmentEntityList(): List<AssignmentEntity> {
    return map { it.toAssignmentEntity() }
}