package com.blackbyte.skucise.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
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
import androidx.compose.material.TextField
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.VisualTransformation
import com.blackbyte.skucise.components.OutlinedInputField
import com.blackbyte.skucise.ui.theme.SkuciSeTheme
import kotlinx.coroutines.launch

@Preview
@Composable
fun HomeScreen() {
    val gradient = Brush.linearGradient(0f to Color.Magenta, 1000f to Color.Yellow)
    val state = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    var query by remember { mutableStateOf("") }


    SkuciSeTheme {
        Scaffold(
            scaffoldState = state,
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

                        Spacer(modifier = Modifier.size(20.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                                contentPadding = PaddingValues(all = 8.dp),
                                modifier = Modifier
                                    .size(48.dp),
                                onClick = {
                                    scope.launch { if (state.drawerState.isClosed) state.drawerState.open() else state.drawerState.close() }
                                }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "menu icon",
                                    tint = Color.Black,
                                    modifier = Modifier
                                        .fillMaxSize()
                                )
                            }
                            Spacer(modifier = Modifier.size(20.dp))
                            TextField(
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.White,
                                    cursorColor = Color.Black,
                                    disabledLabelColor = Color.White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                singleLine = true,
                                value = query,
                                onValueChange = { query = it },
                                leadingIcon = {Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "menu icon",
                                    tint = Color.Black
                                )},
                                label = {
                                    Text("Pretraga...")
                                },
                                textStyle = LocalTextStyle.current,
                                shape = RoundedCornerShape(4.dp),
                                modifier = Modifier
                                    .height(48.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            },
        ) {
        }
    }
}
