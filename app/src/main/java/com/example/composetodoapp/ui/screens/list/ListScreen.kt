package com.example.composetodoapp.ui.screens.list

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.example.composetodoapp.data.models.ToDoTask
import com.example.composetodoapp.ui.theme.fabBackgroundColor
import com.example.composetodoapp.ui.viewmodel.SharedViewModel
import com.example.composetodoapp.utils.RequestState
import com.example.composetodoapp.utils.SearchAppBarState

@ExperimentalMaterialApi
@Composable
fun ListScreen(navigateToTaskScreen: (taskId: Int) -> Unit, sharedViewModel: SharedViewModel) {

    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllTasks()
    }

    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextString: String by sharedViewModel.searchTextString
    val allTasks: RequestState<List<ToDoTask>> by sharedViewModel.allTask.collectAsState()

    Scaffold(
        floatingActionButton = {
            ListFab(
                navigateToTaskScreen = navigateToTaskScreen
            )
        },
        content = {
            ListContent(allTasks, navigateToTaskScreen)
        },
        topBar = {
            ListAppBar(
                sharedViewModel,
                searchAppBarState,
                searchTextString
            )
        })
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