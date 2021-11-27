package com.blackbyte.skucise.components



import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.blackbyte.skucise.ui.theme.MediumGray
import com.blackbyte.skucise.ui.theme.TinyGray

@Composable
fun CustomTextField(placeholder: String = "", modifier: Modifier = Modifier) {

    var text by remember { mutableStateOf("")}

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        modifier = modifier,
        placeholder = { Text(placeholder) }
    )

}