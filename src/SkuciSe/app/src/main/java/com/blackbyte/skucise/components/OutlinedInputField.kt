package com.blackbyte.skucise.components

import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun OutlinedInputField(
    label: String = "",
    onValueChange: (s: String) -> Unit = {},
    readOnly: Boolean = false,
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        readOnly = readOnly,
        onValueChange = {
            text = it
            onValueChange(text)
        },
        label = { Text(label) },
        modifier = modifier
    )
}