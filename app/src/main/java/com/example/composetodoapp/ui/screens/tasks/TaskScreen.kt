package com.example.composetodoapp.ui.screens.tasks

import android.content.Context
import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.composetodoapp.data.models.ToDoTask
import com.example.composetodoapp.ui.viewmodel.SharedViewModel
import com.example.composetodoapp.utils.Action
import com.example.composetodoapp.utils.Action.NO_ACTION

@Composable
fun TaskScreen(
    sharedViewModel: SharedViewModel,
    selectedTask: ToDoTask?,
    navigateToListScreen: (Action) -> Unit
) {

    val title by sharedViewModel.title
    val description by sharedViewModel.description
    val priority by sharedViewModel.priority

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask,
                navigateToListScreen = { action ->
                    if (action == NO_ACTION) {
                        navigateToListScreen(action)
                    } else {
                        if (sharedViewModel.validateFields()) {
                            navigateToListScreen(action)
                        } else {
                            displayToast(context = context)
                        }
                    }
                }
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

fun displayToast(context: Context) {
    Toast.makeText(
        context,
        "Fields Empty",
        Toast.LENGTH_SHORT
    ).show()
}
