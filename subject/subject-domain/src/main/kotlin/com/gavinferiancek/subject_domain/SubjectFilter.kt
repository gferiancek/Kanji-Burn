package com.gavinferiancek.subject_domain

import com.gavinferiancek.core.domain.FilterOrder

sealed class SubjectFilter(
    open val uiValue: String,
    open val order: FilterOrder,
) {
    data class Reading(
        override val uiValue: String = "Reading",
        override val order: FilterOrder = FilterOrder.Ascending,
    ): SubjectFilter(uiValue, order)

    data class Meaning(
        override val uiValue: String = "Meaning",
        override val order: FilterOrder = FilterOrder.Ascending,
    ): SubjectFilter(uiValue, order)

    data class Level(
        override val uiValue: String = "Level",
        override val order: FilterOrder = FilterOrder.Ascending,
    ): SubjectFilter(uiValue, order)
}