package com.blackbyte.skucise.data

import java.time.LocalDate

data class Review(
    val reviewerName: String,
    val reviewerProfileUrl: String,
    val stars: Int,
    val date: LocalDate,
    val contents: String
)
