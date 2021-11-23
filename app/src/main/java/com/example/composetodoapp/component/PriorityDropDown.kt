package com.example.composetodoapp.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetodoapp.R
import com.example.composetodoapp.data.models.Priority
import com.example.composetodoapp.data.models.Priority.HIGH
import com.example.composetodoapp.data.models.Priority.LOW
import com.example.composetodoapp.data.models.Priority.MEDIUM
import com.example.composetodoapp.ui.theme.Typography
import com.example.composetodoapp.ui.theme.taskContentColor
import com.example.composetodoapp.ui.theme.taskItemColor

@Composable
fun PriorityDropDown(
    priority: Priority,
    onPriorityClicked: (Priority) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    val angle by animateFloatAsState(targetValue = if (expanded) 180f else 0f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable { expanded = true }
            .background(color = MaterialTheme.colors.taskItemColor)
            .border(
                1.dp,
                color = Color.Gray,
                shape = MaterialTheme.shapes.small
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Canvas(
            modifier = Modifier
                .size(16.dp)
                .weight(1f)
        ) {
            drawCircle(color = priority.color)
        }
        Text(
            text = priority.name,
            color = MaterialTheme.colors.taskContentColor,
            modifier = Modifier.weight(8f),
            style = Typography.subtitle1
        )
        IconButton(
            onClick = { expanded = true },
            modifier = Modifier
                .weight(1.5f)
                .rotate(angle),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_drop_down),
                contentDescription = "Dropdown Arrow"
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(fraction = 0.94f)
        ) {
            DropdownMenuItem(onClick = {
                expanded = false
                onPriorityClicked(LOW)
            }) {
                PriorityItem(priority = LOW)
            }
            DropdownMenuItem(onClick = {
                expanded = false
                onPriorityClicked(MEDIUM)
            }) {
                PriorityItem(priority = MEDIUM)
            }
            DropdownMenuItem(onClick = {
                expanded = false
                onPriorityClicked(HIGH)
            }) {
                PriorityItem(priority = HIGH)
            }
        }
    }
}

@Preview
@Composable
fun PreviewPriorityDropDown() {
    PriorityDropDown(priority = LOW, onPriorityClicked = {})
}