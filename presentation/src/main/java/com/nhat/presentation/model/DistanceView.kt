package com.nhat.presentation.model

class DistanceView(
    val distanceInMeters: Int
) {
    val distanceStringValue = String.format("%d km", distanceInMeters.metersToKilometers())

    private fun Int.metersToKilometers() = this / 1000
}