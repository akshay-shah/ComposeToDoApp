package com.example.composetodoapp.ui.screens.tasks

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.example.composetodoapp.data.models.ToDoTask
import com.example.composetodoapp.ui.viewmodel.SharedViewModel
import com.example.composetodoapp.utils.Action

@Composable
fun TaskScreen(
    sharedViewModel: SharedViewModel,
    selectedTask: ToDoTask?,
    navigateToListScreen: (Action) -> Unit
) {

    val title by sharedViewModel.title
    val description by sharedViewModel.description
    val priority by sharedViewModel.priority

    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask,
                navigateToListScreen = navigateToListScreen
            )
        },
        content = {
            TaskContent(
                onTitleChange = { sharedViewModel.title.value = it },
                onDescriptionChange = { sharedViewModel.description.value = it },
                onPriorityChange = { sharedViewModel.priority.value = it },
                title = title,
                description = description,
                priority = priority
            )
        }
    )
}