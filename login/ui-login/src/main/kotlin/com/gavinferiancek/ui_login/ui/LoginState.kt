package com.gavinferiancek.ui_login.ui

import com.gavinferiancek.core_domain.state.ProgressBarState

data class LoginState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val hasFetchedData: Boolean = false,
    val apiKey: String = "",
)