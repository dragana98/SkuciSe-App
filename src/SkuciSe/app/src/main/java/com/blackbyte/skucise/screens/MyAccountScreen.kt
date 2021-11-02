package com.blackbyte.skucise.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blackbyte.skucise.components.DropdownButton
import com.blackbyte.skucise.components.NavTopBar
import com.blackbyte.skucise.components.OutlinedInputField
import com.blackbyte.skucise.ui.theme.SkuciSeTheme

@Composable
fun MyAccountScreen(returnToPreviousScreen: () -> Unit) {
    Scaffold(
        topBar = { NavTopBar("Moj nalog", returnToPreviousScreen = returnToPreviousScreen) },
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxHeight()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    contentAlignment = Alignment.TopEnd,
                    modifier = Modifier.size(104.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "registration icon",
                        modifier = Modifier.fillMaxSize()
                    )
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .background(color = MaterialTheme.colors.primary)
                            .size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "registration icon",
                            tint = Color.White,
                            modifier = Modifier
                                .padding(all = 4.dp)
                                .fillMaxSize()
                        )
                    }
                }
                Spacer(modifier = Modifier.size(20.dp))
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.height(104.dp)
                ) {
                    Text(
                        "Dušan",
                        style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Medium)
                    )
                    Text(
                        "Petrović",
                        style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Medium)
                    )
                }
            }
            Spacer(modifier = Modifier.size(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Box(modifier = Modifier.size(size = 6.dp))
                    DropdownButton(
                        hintText = "\uD83C\uDDF7\uD83C\uDDF8 Srbija (+381)",
                        items = listOf("\uD83C\uDDF7\uD83C\uDDF8 Srbija (+381)"),
                        disabled = listOf(),
                        modifier = Modifier.fillMaxWidth(0.5f)
                    )
                }
                Box(modifier = Modifier.size(size = 10.dp))
                OutlinedInputField("Broj telefona", modifier = Modifier.fillMaxWidth())


            }
            Spacer(modifier = Modifier.size(18.dp))
            OutlinedInputField(label = "E-adresa", modifier = Modifier.fillMaxWidth())
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.fillMaxHeight()
            ) {
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

@Preview(showBackground = true)
@Composable
fun MyAccouontScreenPreview() {
    SkuciSeTheme {
        MyAccountScreen({})
    }
}