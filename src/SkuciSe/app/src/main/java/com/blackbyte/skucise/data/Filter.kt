package com.blackbyte.skucise.data

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf

@Stable
class Filter(
    val name: String,
    val id: Int,
    enabled: Boolean = false
){
    val enabled = mutableStateOf(enabled)
}

val listOfCities = listOf(
    Filter("Novi Sad", 0),
    Filter("Niš", 1),
    Filter("Kragujevac", 2),
    Filter("Beograd", 3)

)

val listOfObjects = listOf(
    Filter("Stan", 0),
    Filter("Vikendica", 1),
    Filter("Apartman", 2),
    Filter("Kuća", 3)
)


val listOfAmenities = listOf(
    Filter("Parking", 0),
    Filter("Terasa", 1),
    Filter("Grejanje", 2),
    Filter("Pet-friendly", 3),
    Filter("TV", 4),
    Filter("Bazen", 5),
    Filter("WiFi", 6),
    Filter("Posluga", 7)
)
