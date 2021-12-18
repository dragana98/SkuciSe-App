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
    val id: Int,
    enabled: Boolean = false
){
    var enabled = mutableStateOf(enabled)
}
