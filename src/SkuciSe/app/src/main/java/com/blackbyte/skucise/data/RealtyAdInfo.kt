package com.blackbyte.skucise.data

data class Floor(
    val id: Int,
    val rooms: Int,
    val bathrooms: Int,
    val surface: Int,
    val floorPlanUrl: String,
    val price: Int?,
    val deposit: Int?
)

data class RealtyAdInfo(
    val id: Int,
    val title: String,
    val address: String,
    val description: String,
    val isFavorite: Boolean,
    val amenities: List<String>,
    val images: List<String>,
    val totalReviews: Int,
    val avgScore: Double,
    val monthly: Boolean,
    val unified: Boolean,
    val priceRange: String,
    val deposit: Int?,
    val roomsRange: String,
    val bathroomRange: String,
    val floors: List<Floor>
)