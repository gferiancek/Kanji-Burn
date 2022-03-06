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
    fun getSrsStageName() = when(srsStage) {
        0 -> ""
        1 -> "Apprentice I"
        2 -> "Apprentice II"
        3 -> "Apprentice III"
        4 -> "Apprentice IV"
        5 -> "Guru I"
        6 -> "Guru II"
        7 -> "Master"
        8 -> "Enlightened"
        9 -> "Burned"
        else -> "Unknown"
    }
}
