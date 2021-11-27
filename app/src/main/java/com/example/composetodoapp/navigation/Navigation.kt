package com.example.composetodoapp.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.example.composetodoapp.navigation.destinations.listComposable
import com.example.composetodoapp.navigation.destinations.splashComposable
import com.example.composetodoapp.navigation.destinations.taskComposable
import com.example.composetodoapp.ui.viewmodel.SharedViewModel
import com.example.composetodoapp.utils.Constants.SPLASH_SCREEN
import com.google.accompanist.navigation.animation.AnimatedNavHost

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun SetupNavigation(navController: NavHostController, sharedViewModel: SharedViewModel) {

    val screen = remember(navController) {
        Screens(navController = navController)
    }

    AnimatedNavHost(navController = navController, startDestination = SPLASH_SCREEN) {
        listComposable(
            navigateToTaskScreen = screen.task,
            sharedViewModel = sharedViewModel
        )
        taskComposable(
            navigateToListScreen = screen.list,
            sharedViewModel = sharedViewModel
        )
        splashComposable(
            navigateToListScreen = screen.splash
        )
    }

}