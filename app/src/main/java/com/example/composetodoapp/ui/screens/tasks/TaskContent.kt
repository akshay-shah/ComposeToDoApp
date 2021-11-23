package com.example.composetodoapp.ui.screens.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetodoapp.component.PriorityDropDown
import com.example.composetodoapp.data.models.Priority
import com.example.composetodoapp.data.models.Priority.NONE

@Composable
fun TaskContent(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    priority: Priority,
    onPriorityChange: (Priority) -> Unit
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colors.background)
            .padding(12.dp)
    )
    {
        OutlinedTextField(
            value = title,
            onValueChange = { onTitleChange(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Title") },
            maxLines = 1
        )
        Divider(
            modifier = Modifier
                .height(8.dp),
            color = MaterialTheme.colors.background
        )
        PriorityDropDown(
            priority = priority,
            onPriorityClicked = { onPriorityChange(it) }
        )
        Divider(
            modifier = Modifier
                .height(8.dp),
            color = MaterialTheme.colors.background

        )
        OutlinedTextField(
            value = description,
            onValueChange = { onDescriptionChange(it) },
            modifier = Modifier.fillMaxSize(),
            label = { Text(text = "Description") }
        )

    }
}

@Preview
@Composable
fun PreviewTaskContent() {
    TaskContent(
        onTitleChange = {},
        onDescriptionChange = {},
        onPriorityChange = {},
        title = "",
        description = "",
        priority = NONE
    )
}