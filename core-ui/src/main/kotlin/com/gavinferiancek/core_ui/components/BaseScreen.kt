package com.gavinferiancek.core_ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
                .padding(it)
                .background(MaterialTheme.colors.background)
        ) {

            Crossfade(targetState = progressBarState == ProgressBarState.Idle ) { idle ->
                when {
                    idle -> content()
                    else -> CircularIndeterminateProgressBar()
                }
            }
        }
    }
}