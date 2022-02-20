package com.gavinferiancek.kanjiburn.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.gavinferiancek.kanjiburn.ui.navigation.Screens
import com.gavinferiancek.kanjiburn.ui.theme.KanjiBurnTheme
import com.gavinferiancek.ui_subjectdetail.ui.SubjectDetailScreen
import com.gavinferiancek.ui_subjectdetail.ui.SubjectDetailViewModel
import com.gavinferiancek.ui_subjectlist.ui.SubjectListScreen
import com.gavinferiancek.ui_subjectlist.ui.SubjectListViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    @ExperimentalCoilApi
    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    @ExperimentalFoundationApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KanjiBurnTheme {
                val navController = rememberAnimatedNavController()
                AnimatedNavHost(
                    navController = navController,
                    startDestination = Screens.SubjectList.route,
                    builder = {
                        addSubjectList(
                            navController = navController,
                            imageLoader = imageLoader,
                        )
                        addSubjectDetail(
                            navController = navController,
                            imageLoader = imageLoader,
                        )
                    }
                )
            }
        }
    }
}

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
fun NavGraphBuilder.addSubjectList(
    navController: NavController,
    imageLoader: ImageLoader,
) {
    composable(
        route = Screens.SubjectList.route,
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(300)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(300)
            )
        }
    ) {
        val viewModel: SubjectListViewModel = hiltViewModel()
        SubjectListScreen(
            state = viewModel.state.value,
            events = viewModel::onTriggerEvent,
            imageLoader = imageLoader,
            navigateToDetailScreen = { subjectId ->
                navController.navigate(route = "${Screens.SubjectDetail.route}/$subjectId")
            }
        )
    }
}

@ExperimentalFoundationApi
@ExperimentalCoilApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
fun NavGraphBuilder.addSubjectDetail(
    navController: NavController,
    imageLoader: ImageLoader,
) {
    composable(
        route = Screens.SubjectDetail.route + "/{subjectId}",
        arguments = Screens.SubjectDetail.arguments,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(300))
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(300)
            )
        }
    ) {
        val viewModel: SubjectDetailViewModel = hiltViewModel()
        SubjectDetailScreen(
            state = viewModel.state.value,
            imageLoader = imageLoader,
            events = viewModel::onTriggerEvent,
            onNavigateUp = navController::popBackStack
        )
    }
}