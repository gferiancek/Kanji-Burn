package com.gavinferiancek.core.domain

sealed class ProgressBarState {

    object Idle: ProgressBarState()

    object Loading: ProgressBarState()
}
