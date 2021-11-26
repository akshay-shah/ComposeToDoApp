package com.example.composetodoapp.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.composetodoapp.ui.screens.tasks.TaskScreen
import com.example.composetodoapp.ui.viewmodel.SharedViewModel
import com.example.composetodoapp.utils.Action
import com.example.composetodoapp.utils.Constants.TASK_ARGUMENT_KEY
import com.example.composetodoapp.utils.Constants.TASK_SCREEN

fun NavGraphBuilder.taskComposable(
    sharedViewModel: SharedViewModel,
    navigateToListScreen: (Action) -> Unit
) {
    composable(
        route = TASK_SCREEN,
        arguments = listOf(navArgument(TASK_ARGUMENT_KEY) {
            type = NavType.IntType
        })
    ) { navBackStackEntry ->
        val taskId = navBackStackEntry.arguments!!.getInt(TASK_ARGUMENT_KEY)
        sharedViewModel.getSelectedTask(taskId = taskId)
        val selectedTask by sharedViewModel.selectedTask.collectAsState()

        LaunchedEffect(key1 = selectedTask) {
            if (selectedTask != null || taskId == -1)
                sharedViewModel.updateFields(selectedTask = selectedTask)
        }

        TaskScreen(
            sharedViewModel = sharedViewModel,
            selectedTask = selectedTask,
            navigateToListScreen = navigateToListScreen
        )
    }
}