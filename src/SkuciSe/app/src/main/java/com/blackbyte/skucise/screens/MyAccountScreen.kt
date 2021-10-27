package com.blackbyte.skucise.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blackbyte.skucise.components.NavTopBar
import com.blackbyte.skucise.components.OutlinedInputField
import com.blackbyte.skucise.ui.theme.SkuciSeTheme

@Preview
@Composable
fun MyAccountScreen(returnToPreviousScreen: () -> Unit = {}) {
    SkuciSeTheme {
        Scaffold(
            topBar = { NavTopBar("Moj nalog", returnToPreviousScreen = returnToPreviousScreen) },
            backgroundColor = MaterialTheme.colors.background
        ) {
            Column(modifier = Modifier
                .padding(20.dp)
                .fillMaxHeight()) {
                OutlinedInputField(label = "E-adresa", modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.size(10.dp))
                Button(
                    onClick = {
                        // do something here
                    }, modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Potvrdite izmene")
                }
            }
        }
    }
}