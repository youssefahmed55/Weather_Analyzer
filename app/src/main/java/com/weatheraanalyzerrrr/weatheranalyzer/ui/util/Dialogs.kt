package com.weatheraanalyzerrrr.weatheranalyzer.ui.util

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SimpleDialog(
    showDialog: Boolean,
    title: String,
    text: String,
    onClose: () -> Unit,
    onAccept: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (showDialog) {
        // This composable displays a dialog
        AlertDialog(
            modifier = modifier,
            onDismissRequest = {
                onClose()  // Close the dialog when dismissed
            },
            title = {
                Text(text = title)
            },
            text = {
                Text(text)
            },
            confirmButton = {
                Button(onClick = onAccept) {
                    Text("Accept")
                }
            },
        )
    }
}