package com.gavinferiancek.review_domain

import com.gavinferiancek.core_domain.state.FilterOrderState

sealed class SubjectFilter(
    open val uiValue: String,
    open val orderState: FilterOrderState,
) {
    data class Reading(
        override val uiValue: String = "Reading",
        override val orderState: FilterOrderState = FilterOrderState.Ascending,
    ): SubjectFilter(uiValue, orderState)

    data class Meaning(
        override val uiValue: String = "Meaning",
        override val orderState: FilterOrderState = FilterOrderState.Ascending,
    ): SubjectFilter(uiValue, orderState)

    data class Level(
        override val uiValue: String = "Level",
        override val orderState: FilterOrderState = FilterOrderState.Ascending,
    ): SubjectFilter(uiValue, orderState)
}