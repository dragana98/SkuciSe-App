package com.blackbyte.skucise.data

class PropertyEntry (
    val id: Int,
    val imageUrls: List<String>,
    val propertyName: String,
    val approximateLocation: String,
    //val preciseLocation: String,
    val flats: List<Flat>,
    val addedToFavorites: Boolean,
    val avgReviewRating: Float,
    val reviewCount: Int,
    val description: String,
    val amenities: List<Amenity>,
    val vendor: VendorPublicInfo
)