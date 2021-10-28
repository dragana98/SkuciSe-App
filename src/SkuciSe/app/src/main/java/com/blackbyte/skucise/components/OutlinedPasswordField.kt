package com.blackbyte.skucise.components

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color

@Composable
fun OutlinedPasswordField(label: String = "", modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(label) },
        modifier = modifier,
        trailingIcon = {
            val image = Icons.Filled.Visibility

            IconButton(onClick = {
                //passwordVisibility = !passwordVisibility
            }) {
                Icon(imageVector  = image, "")
            }
        }
    )
}