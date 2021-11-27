package com.example.composetodoapp.ui.screens.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetodoapp.R
import com.example.composetodoapp.ui.theme.taskItemTextColor

@Composable
fun EmptyContent() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_empty_content),
            contentDescription = "",
            tint = MaterialTheme.colors.taskItemTextColor,
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = "No Tasks Found",
            color = MaterialTheme.colors.taskItemTextColor
        )
    }
}

@Preview
@Composable
fun ShowEmptyContent() {
    EmptyContent()
}