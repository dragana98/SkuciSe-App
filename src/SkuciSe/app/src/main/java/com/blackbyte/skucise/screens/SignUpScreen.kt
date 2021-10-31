package com.blackbyte.skucise.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.blackbyte.skucise.components.*
import com.blackbyte.skucise.ui.theme.SkuciSeTheme
import java.time.LocalDate


@Composable
fun SignUpScreen(
    returnToPreviousScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    var showCalendar: Boolean by remember { mutableStateOf(false) }

    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        topBar = { NavTopBar("Registracija", returnToPreviousScreen = returnToPreviousScreen) },
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(all = 20.dp)
        ) {
            OutlinedInputField("Ime", modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.size(size  = 18.dp))
            OutlinedInputField("Prezime", modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.size(size  = 20.dp))
            OutlinedInputField("E-adresa", modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.size(size  = 20.dp))
            OutlinedButton(
                onClick = {
                    showCalendar = true
                },
                modifier = Modifier
                    .height(46.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Datum rođenja")
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = "registration icon"
                    )
                }
            }
            Spacer(modifier = Modifier.size(size  = 20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Spacer(modifier = Modifier.size(size  = 6.dp))
                    DropdownButton(
                        hintText = "\uD83C\uDDF7\uD83C\uDDF8 Srbija (+381)",
                        items = listOf("\uD83C\uDDF7\uD83C\uDDF8 Srbija (+381)"),
                        disabled = listOf(),
                        modifier = Modifier.fillMaxWidth(0.5f)
                    )
                }
                Spacer(modifier = Modifier.size(size  = 10.dp))
                OutlinedInputField("Broj telefona", modifier = Modifier.fillMaxWidth())


            }
            Spacer(modifier = Modifier.size(size  = 20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Spacer(modifier = Modifier.size(size  = 6.dp))
                    DropdownButton(
                        hintText = "Dokument",
                        items = listOf("Lična karta", "Pasoš", "Vozačka dozvola"),
                        disabled = listOf(),
                        modifier = Modifier.fillMaxWidth(0.5f)
                    )
                }
                Spacer(modifier = Modifier.size(size  = 10.dp))
                OutlinedInputField("Broj isprave", modifier = Modifier.fillMaxWidth())
            }
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Više informacija", color = MaterialTheme.colors.primary)
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "registration icon",
                    modifier = Modifier.size(18.dp),
                    tint = MaterialTheme.colors.primary
                )
            }
            Spacer(modifier = Modifier.size(size  = 20.dp))
            OutlinedPasswordField("Lozinka", modifier = Modifier.fillMaxWidth())

            if (showCalendar)
                Popup(onDismissRequest = { showCalendar = false }) {
                    Surface(color = MaterialTheme.colors.primaryVariant) {
                        DatePickerGrid(
                            date = LocalDate.of(2021, 11, 14),
                            onDateSelected = {  }
                        )
                    }
                }
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.fillMaxHeight()
            ) {
                Button(
                    onClick = {
                        navigateToHomeScreen()
                    }, modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Registrujte se")
                }
                Spacer(modifier = Modifier.size(size  = 10.dp))
                Text(
                    text = "Imate nalog? Prijavite se.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SignUpScrenPreview() {
    SkuciSeTheme {
        SignUpScreen({}, {})
    }
}