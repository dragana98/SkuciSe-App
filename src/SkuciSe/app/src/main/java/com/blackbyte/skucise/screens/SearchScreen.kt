package com.blackbyte.skucise.screens

import android.util.Log
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
import androidx.compose.runtime.*
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
    returnToPreviousScreen: () -> Unit,
    toSearchResults: (
        Boolean,
        Boolean,
        List<String>
    ) -> Unit
) {
    var leasable by remember { mutableStateOf(false) }
    var unified by remember { mutableStateOf(false) }

    var selectedCityIndex by remember { mutableStateOf(0) }
    var selectedAmenityIndex by remember { mutableStateOf(0) }
    var selectedRealtyTypeIndex by remember { mutableStateOf(0) }


    var amenitiesIndex = remember { mutableListOf<Int>(0, 0, 0, 0, 0, 0, 0, 0) }
    var citiesIndex = remember { mutableListOf<Int>(0, 0, 0, 0) }

    val amenitiesEncoded = listOf(
        "wifi",
        "petfriendly",
        "terrace",
        "parking",
        "heating",
        "tv",
        "pool",
        "furnished"
    )

    val citiesEncoded = listOf(
        "Novi Sad",
        "Niš",
        "Kragujevac",
        "Beograd"
    )

    val triggerAmenity = fun(index: Int) {
        if (amenitiesIndex[index] == 0) {
            amenitiesIndex[index] = 1
        } else {
            amenitiesIndex[index] = 0
        }
    }

    val triggerCity = fun(index: Int) {
        if (citiesIndex[index] == 0) {
            citiesIndex[index] = 1
        } else {
            citiesIndex[index] = 0
        }
    }

    Scaffold(
        topBar = { NavTopBar("Pretraga", returnToPreviousScreen = returnToPreviousScreen) },
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxHeight() // verticalArrangement = Arrangement.Center
                .verticalScroll(rememberScrollState())
        ) {
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
            ) {
                val selectedCategory = remember { mutableStateOf("") }

                RadioButton(
                    selected = selectedCategory.value == "iznajmiti",
                    onClick = {
                        leasable = true
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
                        leasable = false
                        selectedCategory.value = "kupiti"
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colors.primary,
                        unselectedColor = MaterialTheme.colors.onBackground
                    )
                )
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
            FilterChip(filters = listOfCities, filterTrigger = triggerCity)

            Text(
                text = "Objekat: ",
                fontSize = 19.sp,
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(size = 10.dp))

            // Objects
            FilterChip(filters = listOfObjects, filterTrigger = triggerAmenity)

            // Amenities ----

            Spacer(modifier = Modifier.size(size = 30.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        var cities = mutableListOf<String>()
                        unified =
                            !(listOfObjects[selectedRealtyTypeIndex].name == "Stan" || listOfObjects[selectedRealtyTypeIndex].name == "Apartman")

                        for (i in 0 until citiesIndex.size) {
                            if (citiesIndex[i] == 1) {
                                cities.add(citiesEncoded[i])
                            }
                        }

                        toSearchResults(leasable, unified, cities)

                    },
                    modifier = Modifier
                        .width(170.dp)
                        .clip(
                            RoundedCornerShape(
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
                ) {
                    Text(text = "Pretraži", color = Color.White, fontSize = 16.sp)
                }
            }

        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SkuciSeTheme {
        SearchScreen({})
    }
}
 */
