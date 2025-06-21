package com.example.walkitall.ui.mappage

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun PermissionRationale(
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    AlertDialog(
        title = { Text("Permissions Required") },
        text = { Text("In order to keep track of what routes you have walked, this app needs permission to access your device's location. In order to access your location when the app is in the background, optional background location can also be used.")},
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Continue")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Not Now")
            }
        },
        onDismissRequest = onDismiss
    )
}