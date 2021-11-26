package com.example.composetodoapp.ui.screens.list

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection.EndToStart
import androidx.compose.material.DismissValue.Default
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetodoapp.data.models.Priority
import com.example.composetodoapp.data.models.Priority.HIGH
import com.example.composetodoapp.data.models.Priority.LOW
import com.example.composetodoapp.data.models.ToDoTask
import com.example.composetodoapp.ui.theme.Typography
import com.example.composetodoapp.ui.theme.taskContentColor
import com.example.composetodoapp.ui.theme.taskItemColor
import com.example.composetodoapp.utils.Action
import com.example.composetodoapp.utils.Action.DELETE
import com.example.composetodoapp.utils.RequestState
import com.example.composetodoapp.utils.SearchAppBarState
import com.example.composetodoapp.utils.SearchAppBarState.TRIGGERED

@ExperimentalMaterialApi
@Composable
fun ListContent(
    sortState: RequestState<Priority>,
    lowPriorityTasks: List<ToDoTask>,
    highPriorityTasks: List<ToDoTask>,
    allTasks: RequestState<List<ToDoTask>>,
    searchTasks: RequestState<List<ToDoTask>>,
    searchAppBarState: SearchAppBarState,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
) {
    if (sortState is RequestState.Success) {
        when {
            searchAppBarState == TRIGGERED -> {
                HandleListContent(
                    onSwipeToDelete = onSwipeToDelete,
                    tasks = searchTasks,
                    navigateToTaskScreen = navigateToTaskScreen
                )
            }
            sortState.data == Priority.NONE -> {
                HandleListContent(
                    tasks = allTasks,
                    onSwipeToDelete = onSwipeToDelete,
                    navigateToTaskScreen = navigateToTaskScreen
                )
            }
            sortState.data == LOW -> {
                HandleListContent(
                    tasks = RequestState.Success(lowPriorityTasks),
                    onSwipeToDelete = onSwipeToDelete,
                    navigateToTaskScreen = navigateToTaskScreen
                )
            }
            sortState.data == HIGH -> {
                HandleListContent(
                    tasks = RequestState.Success(highPriorityTasks),
                    onSwipeToDelete = onSwipeToDelete,
                    navigateToTaskScreen = navigateToTaskScreen
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun HandleListContent(
    tasks: RequestState<List<ToDoTask>>,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    if (tasks is RequestState.Success) {
        if (tasks.data.isNotEmpty()) {
            DisplayTasks(
                onSwipeToDelete = onSwipeToDelete,
                allTasks = tasks.data,
                navigateToTaskScreen = navigateToTaskScreen
            )
        } else {
            EmptyContent()
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun DisplayTasks(
    allTasks: List<ToDoTask>,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    LazyColumn {
        items(
            items = allTasks,
            key = {
                it.id
            },
            {
                val swipeState = rememberDismissState()
                val direction = swipeState.dismissDirection
                val isDismissed = swipeState.isDismissed(direction = EndToStart)
                if (isDismissed && direction == EndToStart) {
                    onSwipeToDelete(DELETE, it)
                }
                val degrees by animateFloatAsState(
                    targetValue =
                    if (swipeState.targetValue == Default) {
                        0f
                    } else {
                        -45f
                    }
                )

                SwipeToDismiss(
                    state = swipeState,
                    background = { RedBackGround(degress = degrees) },
                    dismissThresholds = { FractionalThreshold(0.3f) },
                    directions = setOf(EndToStart),
                    dismissContent = {
                        TaskItem(
                            toDoTask = it,
                            navigateToTaskScreen = navigateToTaskScreen
                        )
                    }
                )
            }
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun TaskItem(toDoTask: ToDoTask, navigateToTaskScreen: (taskId: Int) -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RectangleShape,
        onClick = { navigateToTaskScreen(toDoTask.id) },
        color = MaterialTheme.colors.taskItemColor,
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = toDoTask.title,
                    color = MaterialTheme.colors.taskContentColor,
                    modifier = Modifier.weight(0.9f),
                    style = Typography.h5,
                    fontWeight = FontWeight.Bold
                )
                Canvas(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .width(16.dp)
                        .height(16.dp)
                        .weight(0.1f),
                    onDraw = {
                        drawCircle(color = toDoTask.priority.color)
                    }
                )
            }
            Text(
                text = toDoTask.description,
                color = MaterialTheme.colors.taskContentColor,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}

@Composable
fun RedBackGround(degress: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
            .padding(24.dp),
        contentAlignment = Alignment.CenterEnd,
    ) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = "delete icon",
            tint = Color.White,
            modifier = Modifier.rotate(degress)
        )
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewTaskItem() {
    TaskItem(
        toDoTask = ToDoTask(
            id = 1,
            title = "Hello World",
            description = "Description",
            priority = LOW
        ),
        navigateToTaskScreen = {}
    )
}

@Preview
@Composable
fun PreviewRedBackGround() {
    RedBackGround(degress = 0f)
}