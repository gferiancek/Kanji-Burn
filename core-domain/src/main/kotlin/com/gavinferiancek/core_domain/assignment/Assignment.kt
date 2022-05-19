package com.gavinferiancek.core_domain.assignment

data class Assignment(
    val srsStage: Int,
    val unlockedAt: String?,
    val startedAt: String?,
    val passedAt: String?,
    val burnedAt: String?,
    val availableAt: String?,
    val resurrectedAt: String?,
    val hidden: Boolean,
) {

}
