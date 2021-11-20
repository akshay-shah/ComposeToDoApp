package com.example.composetodoapp.ui.screens.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetodoapp.R
import com.example.composetodoapp.component.PriorityItem
import com.example.composetodoapp.data.models.Priority
import com.example.composetodoapp.data.models.Priority.HIGH
import com.example.composetodoapp.data.models.Priority.LOW
import com.example.composetodoapp.data.models.Priority.NONE
import com.example.composetodoapp.ui.theme.Typography
import com.example.composetodoapp.ui.theme.topAppBarBackgroundColor
import com.example.composetodoapp.ui.theme.topAppBarContentColor
import com.example.composetodoapp.ui.viewmodel.SharedViewModel
import com.example.composetodoapp.utils.SearchAppBarState
import com.example.composetodoapp.utils.SearchAppBarState.CLOSED
import com.example.composetodoapp.utils.SearchAppBarState.OPENED

@Composable
fun ListAppBar(
    sharedViewModel: SharedViewModel,
    searchAppBarState: SearchAppBarState,
    searchTextString: String
) {
    when (searchAppBarState) {
        CLOSED -> {
            DefaultAppBar(
                onSearchClicked = { sharedViewModel.searchAppBarState.value = OPENED },
                onSortClicked = {},
                onDeleteClicked = {}
            )
        }
        else -> SearchAppBar(
            text = searchTextString,
            onTextChange = { sharedViewModel.searchTextString.value = it },
            onCloseClick = {
                sharedViewModel.searchAppBarState.value = CLOSED
                sharedViewModel.searchTextString.value = ""
            },
            onSearchClicked = {}
        )

    }
}

@Composable
fun DefaultAppBar(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Tasks",
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            ListAppBarAction(onSearchClicked, onSortClicked, onDeleteClicked)
        }
    )
}

@Composable
fun ListAppBarAction(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteClicked: () -> Unit
) {
    SearchAction(onSearchClicked = onSearchClicked)
    SortAction(onSortClicked = onSortClicked)
    DeleteAction(onDeleteClicked = onDeleteClicked)
}

@Composable
fun DeleteAction(onDeleteClicked: () -> Unit) {
    var expanded by remember {
        mutableStateOf(false)
    }
    IconButton(onClick = { expanded = true }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
            contentDescription = ""
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = {
                expanded = false
                onDeleteClicked()
            }) {
                Text(
                    text = "Delete All",
                    style = Typography.subtitle2,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
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
                onSortClicked(HIGH)
            }) {
                PriorityItem(priority = HIGH)
            }
            DropdownMenuItem(onClick = {
                expanded = false
                onSortClicked(NONE)
            }) {
                PriorityItem(priority = NONE)
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

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClick: () -> Unit,
    onSearchClicked: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.topAppBarBackgroundColor
    ) {
        TextField(
            value = text,
            onValueChange = onTextChange,
            placeholder = {
                Text(
                    text = "Search",
                    color = Color.White,
                    modifier = Modifier.alpha(ContentAlpha.medium)
                )
            },
            textStyle = TextStyle(
                color = MaterialTheme.colors.topAppBarContentColor,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(onClick = { onSearchClicked(text) }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "",
                        tint = MaterialTheme.colors.topAppBarContentColor,
                        modifier = Modifier.alpha(ContentAlpha.disabled)
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    if (text.isNotEmpty()) {
                        onTextChange("")
                    } else {
                        onCloseClick()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "",
                        tint = MaterialTheme.colors.topAppBarContentColor
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { onSearchClicked(text) }
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = MaterialTheme.colors.topAppBarContentColor,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent
            )
        )
    }
}

@Preview
@Composable
fun SearchBarPreview() {
    SearchAppBar(
        text = "",
        onTextChange = {},
        onCloseClick = {},
        onSearchClicked = {}
    )
}

@Preview
@Composable
fun AppBarPreview() {
    DefaultAppBar(
        onSearchClicked = {},
        onSortClicked = {},
        onDeleteClicked = {}
    )
}