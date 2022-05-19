package com.gavinferiancek.ui_login.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gavinferiancek.core_domain.state.ProgressBarState
import com.gavinferiancek.core_ui.components.BaseScreen

@Composable
fun LoginScreen(
    state: LoginState,
    events: (LoginEvents) -> Unit,
    navigateToReviewList: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {

    }
    BaseScreen(
        progressBarState = state.progressBarState,
        topBar = { },
    ) {
        if (state.progressBarState is ProgressBarState.Idle && state.hasFetchedData) {
            navigateToReviewList()
        }
    }
}