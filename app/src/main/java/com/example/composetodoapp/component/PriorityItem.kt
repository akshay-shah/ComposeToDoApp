package com.example.composetodoapp.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetodoapp.data.models.Priority
import com.example.composetodoapp.data.models.Priority.HIGH
import com.example.composetodoapp.ui.theme.Typography

@Composable
fun PriorityItem(priority: Priority) {
    Row {
        Spacer(modifier = Modifier.width(10.dp))
        Canvas(
            modifier = Modifier
                .size(16.dp)
                .align(Alignment.CenterVertically)
        ) {
            drawCircle(color = priority.color)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            style = Typography.subtitle2,
            text = priority.name
        )
        Spacer(modifier = Modifier.width(10.dp))
    }
}

@Preview
@Composable
fun PriorityItemPreview() {
    PriorityItem(priority = HIGH)
}