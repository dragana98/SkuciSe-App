package com.blackbyte.skucise.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NavTopBar(title: String, returnToPreviousScreen: () -> Unit) {
    TopAppBar(title = {
        Button(
            elevation = ButtonDefaults.elevation(0.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
            onClick = {
                returnToPreviousScreen()
            }) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "arrow back",
                Modifier.size(size = 32.dp)
            )
        }
        Box(modifier = Modifier.size(size = 12.dp)) {}
        Text(title)
    })
}