package com.example.composetodoapp.ui.screens.list

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.SnackbarResult.ActionPerformed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import com.example.composetodoapp.component.AlertDialogComponent
import com.example.composetodoapp.data.models.ToDoTask
import com.example.composetodoapp.ui.theme.fabBackgroundColor
import com.example.composetodoapp.ui.viewmodel.SharedViewModel
import com.example.composetodoapp.utils.Action
import com.example.composetodoapp.utils.Action.ADD
import com.example.composetodoapp.utils.Action.DELETE
import com.example.composetodoapp.utils.Action.DELETE_ALL
import com.example.composetodoapp.utils.Action.NO_ACTION
import com.example.composetodoapp.utils.Action.UNDO
import com.example.composetodoapp.utils.Action.UPDATE
import com.example.composetodoapp.utils.RequestState
import com.example.composetodoapp.utils.SearchAppBarState
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun ListScreen(navigateToTaskScreen: (taskId: Int) -> Unit, sharedViewModel: SharedViewModel) {

    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllTasks()
    }

    val action by sharedViewModel.action

    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextString: String by sharedViewModel.searchTextString
    val allTasks: RequestState<List<ToDoTask>> by sharedViewModel.allTask.collectAsState()
    val searchTasks: RequestState<List<ToDoTask>> by sharedViewModel.searchTasks.collectAsState()
    val state = rememberScaffoldState()
    val openDialog by sharedViewModel.showDeleteAllDialog

    DisplaySnackBar(
        state = state,
        databaseAction = { sharedViewModel.performDataBaseOperation(action = action) },
        undoClicked = { sharedViewModel.action.value = it },
        title = sharedViewModel.title.value,
        action = action
    )

    AlertDialogComponent(
        openDialog = openDialog,
        closeDialog = { sharedViewModel.showDeleteAllDialog.value = false },
        onConfirmClicked = {
            sharedViewModel.showDeleteAllDialog.value = false
            sharedViewModel.action.value = DELETE_ALL
        },
        title = "Delete all tasks",
        message = "Are you sure you want to delete all tasks ?"
    )

    Scaffold(
        floatingActionButton = {
            ListFab(
                navigateToTaskScreen = navigateToTaskScreen
            )
        },
        content = {
            ListContent(
                allTasks = allTasks,
                searchTasks = searchTasks,
                navigateToTaskScreen = navigateToTaskScreen,
                searchAppBarState = searchAppBarState
            )
        },
        topBar = {
            ListAppBar(
                sharedViewModel,
                searchAppBarState,
                searchTextString
            )
        },
        scaffoldState = state
    )
}

@Composable
fun ListFab(navigateToTaskScreen: (taskId: Int) -> Unit) {
    FloatingActionButton(
        onClick = { navigateToTaskScreen(-1) },
        backgroundColor = MaterialTheme.colors.fabBackgroundColor
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add Button",
            tint = Color.White
        )
    }

}

@Composable
fun DisplaySnackBar(
    state: ScaffoldState,
    title: String,
    databaseAction: () -> Unit,
    undoClicked: (Action) -> Unit,
    action: Action
) {
    databaseAction()
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != NO_ACTION) {
            scope.launch {
                val snackBarResult = state.snackbarHostState.showSnackbar(
                    message = setMessage(action, title),
                    actionLabel = setSnackBarLabel(action = action),
                )
                UndoFunction(snackBarResult, action, undoClicked)
            }
        }
    }
}

private fun setMessage(action: Action, title: String): String = when (action) {
    ADD -> "Added task $title"
    UPDATE -> "Updated task $title"
    DELETE -> "Deleted task $title"
    DELETE_ALL -> "Deleted all tasks"
    UNDO -> "$title task undo"
    else -> ""
}

private fun UndoFunction(
    snackBarResult: SnackbarResult,
    action: Action,
    undoClicked: (Action) -> Unit
) =
    when {
        snackBarResult == ActionPerformed && action == DELETE -> {
            undoClicked(UNDO)
        }
        else -> {
        }
    }

private fun setSnackBarLabel(action: Action): String =
    if (action == DELETE) {
        "Undo"
    } else {
        "Ok"
    }
