package com.example.composetodoapp.ui.screens.tasks

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.example.composetodoapp.data.models.ToDoTask
import com.example.composetodoapp.utils.Action

@Composable
fun TaskScreen(
    selectedTask: ToDoTask?,
    navigateToListScreen: (Action) -> Unit
) {
    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask,
                navigateToListScreen = navigateToListScreen
            )
        },
        content = {}
    )
}