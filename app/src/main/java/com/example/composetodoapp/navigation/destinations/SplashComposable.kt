package com.example.composetodoapp.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavGraphBuilder
import com.example.composetodoapp.ui.screens.splash.SplashScreen
import com.example.composetodoapp.utils.Constants
import com.google.accompanist.navigation.animation.composable

@ExperimentalAnimationApi
fun NavGraphBuilder.splashComposable(navigateToListScreen: () -> Unit) {
    composable(
        route = Constants.SPLASH_SCREEN,
        exitTransition = { _, _ ->
            slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(600)
            )
        }
    ) { navBackStackEntry ->
        SplashScreen(navigateToListScreen = navigateToListScreen)
    }
}