package com.example.composetodoapp.ui.screens.list

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.composetodoapp.R
import com.example.composetodoapp.component.PriorityItem
import com.example.composetodoapp.data.models.Priority
import com.example.composetodoapp.data.models.Priority.HIGH
import com.example.composetodoapp.data.models.Priority.LOW
import com.example.composetodoapp.data.models.Priority.MEDIUM
import com.example.composetodoapp.ui.theme.topAppBarBackgroundColor
import com.example.composetodoapp.ui.theme.topAppBarContentColor

@Composable
fun ListAppBar() {
    DefaultAppBar(
        onSearchClicked = {},
        onSortClicked = {}
    )
}

@Composable
fun DefaultAppBar(onSearchClicked: () -> Unit, onSortClicked: (Priority) -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "Tasks",
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            ListAppBarAction(onSearchClicked, onSortClicked)
        }
    )
}

@Composable
fun ListAppBarAction(onSearchClicked: () -> Unit, onSortClicked: (Priority) -> Unit) {
    SearchAction(onSearchClicked = onSearchClicked)
    SortAction(onSortClicked = onSortClicked)
}

@Composable
fun SortAction(onSortClicked: (Priority) -> Unit) {
    var expanded by remember {
        mutableStateOf(false)
    }
    IconButton(onClick = { expanded = true }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_filter_list_24),
            contentDescription = ""
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = {
                expanded = false
                onSortClicked(LOW)
            }) {
                PriorityItem(priority = LOW)
            }
            DropdownMenuItem(onClick = {
                expanded = false
                onSortClicked(MEDIUM)
            }) {
                PriorityItem(priority = MEDIUM)
            }
            DropdownMenuItem(onClick = {
                expanded = false
                onSortClicked(HIGH)
            }) {
                PriorityItem(priority = HIGH)
            }
        }
    }
}

@Composable
fun SearchAction(onSearchClicked: () -> Unit) {
    IconButton(onClick = { onSearchClicked() }) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = "Search Action",
            tint = MaterialTheme.colors.topAppBarContentColor,
        )
    }
}

@Preview
@Composable
fun AppBarPreview() {
    DefaultAppBar({}, {})
}