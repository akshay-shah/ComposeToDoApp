package com.example.composetodoapp.component

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun AlertDialogComponent(
    openDialog: Boolean,
    closeDialog: () -> Unit,
    onConfirmClicked: () -> Unit,
    title: String,
    message: String
) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = { closeDialog() },
            title = {
                Text(text = title)
            },
            text = {
                Text(text = message)
            },
            confirmButton = {
                Button(onClick = {
                    onConfirmClicked()
                    closeDialog()
                }) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = {
                    closeDialog()
                }) {
                    Text(text = "No")
                }
            }
        )
    }
}