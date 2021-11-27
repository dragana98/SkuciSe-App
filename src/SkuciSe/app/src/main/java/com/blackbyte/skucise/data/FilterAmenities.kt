package com.blackbyte.skucise.data


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector

@Stable
class FilterAmenities(
    val name: String,
    val icon: ImageVector,
    enabled: Boolean = false
){
    val enabled = mutableStateOf(enabled)
}

val amenityList = listOf(
    FilterAmenities("Parking",icon = Icons.Filled.LocalParking),
    FilterAmenities("Terasa",icon = Icons.Filled.Balcony),
    FilterAmenities("Grejanje",icon = Icons.Filled.Fireplace),
    FilterAmenities("Pet-friendly",icon = Icons.Filled.Pets),
    FilterAmenities("TV",icon = Icons.Filled.Tv),
    FilterAmenities("Bazen",icon = Icons.Filled.Pool),
    FilterAmenities("WiFi",icon = Icons.Filled.Wifi),
    FilterAmenities("Teretana",icon = Icons.Filled.FitnessCenter)
)
