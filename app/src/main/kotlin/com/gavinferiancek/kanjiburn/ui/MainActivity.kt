package com.gavinferiancek.kanjiburn.ui

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.gavinferiancek.core_ui.theme.KanjiBurnTheme
import com.gavinferiancek.kanjiburn.ui.navigation.Screens
import com.gavinferiancek.ui_login.ui.LoginScreen
import com.gavinferiancek.ui_login.ui.LoginViewModel
import com.gavinferiancek.ui_reviewDetail.ui.ReviewDetailScreen
import com.gavinferiancek.ui_reviewDetail.ui.ReviewDetailViewModel
import com.gavinferiancek.ui_reviewList.ui.ReviewListScreen
import com.gavinferiancek.ui_reviewList.ui.ReviewListViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KanjiBurnTheme {
                val navController = rememberAnimatedNavController()
                AnimatedNavHost(
                    navController = navController,
                    startDestination = Screens.Login.route,
                    builder = {
                        addLoginScreen(
                            navController = navController,
                        )
                        addReviewListScreen(
                            navController = navController,
                            imageLoader = imageLoader,
                        )
                        addReviewDetailScreen(
                            navController = navController,
                            imageLoader = imageLoader,
                        )
                    }
                )
            }
        }
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addLoginScreen(
    navController: NavController,
) {
    composable(
        route = Screens.Login.route,
    ) {
        val viewModel: LoginViewModel = hiltViewModel()
        LoginScreen(
            state = viewModel.state.value,
            events = viewModel::onTriggerEvent,
            navigateToReviewList = {
                navController.navigate(route = Screens.ReviewList.route) {
                    popUpTo(Screens.Login.route) { inclusive = true }
                    launchSingleTop = true
                }
            }
        )
    }
}

@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
fun NavGraphBuilder.addReviewListScreen(
    navController: NavController,
    imageLoader: ImageLoader,
) {
    composable(
        route = Screens.ReviewList.route,
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(700)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(700)
            )
        },
    ) {
        val viewModel: ReviewListViewModel = hiltViewModel()
        ReviewListScreen(
            state = viewModel.state.value,
            events = viewModel::onTriggerEvent,
            imageLoader = imageLoader,
            navigateToDetailScreen = { subjectId ->
                navController.navigate(route = "${Screens.ReviewDetail.route}/$subjectId")
            }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalCoilApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
fun NavGraphBuilder.addReviewDetailScreen(
    navController: NavController,
    imageLoader: ImageLoader,
) {
    composable(
        route = Screens.ReviewDetail.route + "/{subjectId}",
        arguments = Screens.ReviewDetail.arguments,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(700)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(700)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(700)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(700)
            )
        },
    ) {
        val viewModel: ReviewDetailViewModel = hiltViewModel()
        val mediaPlayer = MediaPlayer().apply {
            setOnPreparedListener { start() }
            setOnCompletionListener { reset() }
        }

        DisposableEffect(key1 = viewModel) {
            onDispose {
                Log.d("DisposableEffect", "releasing MediaPlayer")
                mediaPlayer.release()
            }
        }

        ReviewDetailScreen(
            state = viewModel.state.value,
            imageLoader = imageLoader,
            mediaPlayer = mediaPlayer,
            events = viewModel::onTriggerEvent,
            onNavigateUp = navController::popBackStack,
            navigateToDetailScreen = { subjectId ->
                navController.navigate(route = "${Screens.ReviewDetail.route}/$subjectId")
            },
        )
    }
}