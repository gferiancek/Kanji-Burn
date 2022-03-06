package com.gavinferiancek.core_domain.state

sealed class ProgressBarState {

    object Idle: ProgressBarState()

    object Loading: ProgressBarState()
}
