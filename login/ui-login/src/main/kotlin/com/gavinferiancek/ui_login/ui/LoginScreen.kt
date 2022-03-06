package com.gavinferiancek.ui_login.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gavinferiancek.components.BaseScreen
import com.gavinferiancek.core_domain.state.ProgressBarState

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
        events(LoginEvents.FetchData)
        if (state.progressBarState is ProgressBarState.Idle && state.hasFetchedData) {
            navigateToReviewList()
        }
    }
}