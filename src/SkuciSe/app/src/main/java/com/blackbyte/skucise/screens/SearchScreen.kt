package com.blackbyte.skucise.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blackbyte.skucise.R
import com.blackbyte.skucise.components.*
import com.blackbyte.skucise.data.*
import com.blackbyte.skucise.ui.theme.Cyan
import com.blackbyte.skucise.ui.theme.SkuciSeTheme
import com.blackbyte.skucise.ui.theme.TinyGray


@Composable
fun SearchScreen(
    returnToPreviousScreen: () -> Unit
) {
        Scaffold(
            topBar = { NavTopBar("Pretraga", returnToPreviousScreen = returnToPreviousScreen) },
            backgroundColor = MaterialTheme.colors.background
        ) {
            Column(modifier = Modifier
                .padding(20.dp)
                .fillMaxHeight() // verticalArrangement = Arrangement.Center
                .verticalScroll(rememberScrollState())
            ) {

                SearchField("Pretraga...",modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.size(size = 30.dp))

                Text(
                    text = "Nekretninu želim: ",
                    fontSize = 19.sp,
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ){
                    val selectedCategory = remember { mutableStateOf("") }

                    RadioButton(
                        selected = selectedCategory.value == "iznajmiti",
                        onClick = {
                        selectedCategory.value = "iznajmiti"
                    },
                        colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colors.primary,
                        unselectedColor = MaterialTheme.colors.onBackground
                    )
                    )
                    Text("iznajmiti")

                    Spacer(modifier = Modifier.size(size = 70.dp))

                    RadioButton(
                        selected = selectedCategory.value == "kupiti",
                        onClick = {
                        selectedCategory.value = "kupiti"
                    },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colors.primary,
                            unselectedColor = MaterialTheme.colors.onBackground
                        ))
                    Text("kupiti")

                }
                Spacer(modifier = Modifier.size(size = 15.dp))

                Text(
                    text = "Grad: ",
                    fontSize = 19.sp,
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(size = 10.dp))

                // filterChip
                FilterChip(filters = listOfCities)

                Text(
                    text = "Objekat: ",
                    fontSize = 19.sp,
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(size = 10.dp))

                // Objects
                FilterChip(filters = listOfObjects)

                // Amenities ----

                Spacer(modifier = Modifier.size(size = 30.dp))

                Text(
                    text = "Pogodnosti: ",
                    fontSize = 19.sp,
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(size = 10.dp))

                FilterChipAmenities(filters = amenityList)

                Spacer(modifier = Modifier.size(17.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            returnToPreviousScreen()
                        },
                        modifier = Modifier
                            .width(170.dp)
                            .clip( RoundedCornerShape(
                                topStartPercent = 40,
                                bottomEndPercent = 40,
                                bottomStartPercent = 40,
                                topEndPercent = 40
                            )
                            )
                            .padding(5.dp)
                            .height(45.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = TinyGray,
                            contentColor = Color.White
                        )
                    ){
                        Text(text = "Poništi", color = Color.Black, fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.size(12.dp))

                    Button(
                        onClick = {

                        },
                        modifier = Modifier
                            .width(170.dp)
                            .clip( RoundedCornerShape(
                                topStartPercent = 40,
                                bottomEndPercent = 40,
                                bottomStartPercent = 40,
                                topEndPercent = 40
                            )
                            )
                            .padding(5.dp)
                            .height(45.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primary,
                            contentColor = Color.White
                        )
                    ){
                        Text(text = "Primeni", color = Color.White, fontSize = 16.sp)
                    }

                }

            }
        }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview () {
    SkuciSeTheme {
        SearchScreen({})
    }
}
