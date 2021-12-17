package com.blackbyte.skucise.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import com.blackbyte.skucise.R

enum class Amenity(val iconDrawableID: Int, val text: String) {
    PARKING(iconDrawableID = R.drawable.car, text = "Parking"),
    TERRACE(iconDrawableID = R.drawable.terrace, text = "Terasa"),
    HEATING(iconDrawableID = R.drawable.heating, text = "Grejanje"),
    PET_FRIENDLY(iconDrawableID = R.drawable.dog, text = "Pet-friendly"),
    TV(iconDrawableID = R.drawable.tv, text = "TV"),
    POOL(iconDrawableID = R.drawable.ladder, text = "Bazen"),
    WIFI(iconDrawableID = R.drawable.wifi, text = "WiFi"),
    FURNISHED(iconDrawableID = R.drawable.sofa, text = "Namešteno")
}

public fun resolveAmenity(string: String) : Amenity {
    when (string) {
        "wifi" -> return Amenity.WIFI
        "petfriendly" -> return Amenity.PET_FRIENDLY
        "terrace" -> return Amenity.TERRACE
        "parking" -> return Amenity.PARKING
        "heating" -> return Amenity.HEATING
        "tv" -> return Amenity.TV
        "pool" -> return Amenity.POOL
    }
    return Amenity.FURNISHED
}