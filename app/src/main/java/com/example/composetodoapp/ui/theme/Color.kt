package com.example.composetodoapp.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val LightGray = Color.LightGray
val MediumGray = Color.Gray
val DarkGray = Color.DarkGray

val HighPriorityColor = Color.Red
val LowPriorityColor = Color.Blue
val MediumPriorityColor = Color.Yellow
val NonePriorityColor = Color.Gray

val Colors.topAppBarContentColor: Color
    @Composable
    get() = if (isLight) Color.White else LightGray

val Colors.topAppBarBackgroundColor: Color
    @Composable
    get() = if (isLight) Purple500 else Color.Black

val Colors.fabBackgroundColor: Color
    @Composable
    get() = if (isLight) Teal200 else Purple700

val Colors.taskItemColor: Color
    @Composable
    get() = if (isLight) Color.White else DarkGray

val Colors.taskContentColor: Color
    @Composable
    get() = if (isLight) Color.DarkGray else Color.LightGray