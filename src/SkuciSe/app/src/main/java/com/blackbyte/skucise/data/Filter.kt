package com.blackbyte.skucise.data

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf

@Stable
class Filter(
    val name: String,
    enabled: Boolean = false
){
    val enabled = mutableStateOf(enabled)
}

val listOfCities = listOf(
    Filter("Novi Sad"),
    Filter("Jagodina"),
    Filter("Negotin"),
    Filter("Nis"),
    Filter("Kragujevac"),
    Filter("Beograd"),
    Filter("Pozarevac"),
    Filter("Sabac")

)

val listOfObjects = listOf(
    Filter("Stan"),
    Filter("Vikendica"),
    Filter("Apartman"),
    Filter("Kuća")

)