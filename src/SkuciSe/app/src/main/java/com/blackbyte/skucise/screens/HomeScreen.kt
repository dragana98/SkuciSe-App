package com.blackbyte.skucise.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blackbyte.skucise.R
import com.blackbyte.skucise.ui.theme.SkuciSeTheme

@Preview
@Composable
fun HomeScreen() {
    val gradient = Brush.linearGradient(0f to Color.Magenta, 1000f to Color.Yellow)

    SkuciSeTheme {
        Scaffold(
            drawerContent = {
                Text("Drawer title", modifier = Modifier.padding(16.dp))
                Divider()
                // Drawer items
            },
            topBar = {
                Box(
                    modifier = Modifier
                        .background(brush = gradient, alpha = 0.5f)
                        .padding(20.dp)
                ) {
                    Column {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logomark_black),
                                contentScale = ContentScale.Inside,
                                contentDescription = "Skuci Se icon",
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Text("Skući Se", fontWeight = FontWeight.Bold)
                        }

                    }
                }
            },
        ) {
        }
    }
}
