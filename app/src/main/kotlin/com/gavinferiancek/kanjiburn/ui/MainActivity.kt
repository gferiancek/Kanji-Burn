package com.gavinferiancek.kanjiburn.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.gavinferiancek.kanjiburn.ui.navigation.Screens
import com.gavinferiancek.kanjiburn.ui.theme.KanjiBurnTheme
import com.gavinferiancek.ui_subjectlist.ui.SubjectListScreen
import com.gavinferiancek.ui_subjectlist.ui.SubjectListViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KanjiBurnTheme {
                val navController = rememberAnimatedNavController()
                BoxWithConstraints {
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = Screens.SubjectList.route,
                        builder = {
                            addSubjectList(
                                navController = navController,
                            )
                        }
                    )
                }
            }
        }
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addSubjectList(
    navController: NavController,
) {
    composable(
        route = Screens.SubjectList.route,
    ) {
        val viewModel: SubjectListViewModel = hiltViewModel()
        SubjectListScreen(
            state = viewModel.state.value,
            events = viewModel::onTriggerEvent,
        )
    }
}