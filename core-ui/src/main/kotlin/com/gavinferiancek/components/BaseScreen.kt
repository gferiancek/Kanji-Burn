package com.gavinferiancek.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gavinferiancek.core_domain.state.ProgressBarState

@Composable
fun BaseScreen(
    progressBarState: ProgressBarState = ProgressBarState.Idle,
    topBar: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = topBar,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            if (progressBarState is ProgressBarState.Loading) CircularIndeterminateProgressBar()
            else content()
        }
    }
}