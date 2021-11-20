package com.example.composetodoapp.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.composetodoapp.navigation.destinations.taskComposable
import com.example.composetodoapp.ui.viewmodel.SharedViewModel
import com.example.composetodoapp.utils.Constants.LIST_SCREEN
import listComposable

@ExperimentalMaterialApi
@Composable
fun setupNavigation(navController: NavHostController, sharedViewModel: SharedViewModel) {

    val screen = remember(navController) {
        Screens(navController = navController)
    }

    NavHost(navController = navController, startDestination = LIST_SCREEN) {
        listComposable(navigateToTaskScreen = screen.task, sharedViewModel = sharedViewModel)
        taskComposable(navigateToListScreen = screen.list)
    }

}