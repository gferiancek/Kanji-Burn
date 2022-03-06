package com.gavinferiancek.core_domain.state

sealed class FilterOrderState {

    object Ascending: FilterOrderState()

    object Descending: FilterOrderState()
}
