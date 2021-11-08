package com.blackbyte.skucise.data

abstract class Flat(
    val numberOfRooms: Int,
    val numberOfBathrooms: Int,
    val surfaceSquareMeters: Int,
    val availableUnits: Int?,
    val floorPlanUrl: String
    )