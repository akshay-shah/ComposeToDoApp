package com.example.composetodoapp.ui.screens.tasks

import android.content.Context
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
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

    BackPressHandler(onBackPressCallBack = {
        navigateToListScreen(NO_ACTION)
    })

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

@Composable
fun BackPressHandler(
    backPressDispatcher: OnBackPressedDispatcher? = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    onBackPressCallBack: () -> Unit
) {
    val currentBackPressed by rememberUpdatedState(newValue = onBackPressCallBack)

    val backCallBack = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentBackPressed()
            }
        }
    }

    DisposableEffect(key1 = backPressDispatcher) {
        backPressDispatcher?.addCallback(backCallBack)
        onDispose {
            backCallBack.remove()
        }
    }
}
