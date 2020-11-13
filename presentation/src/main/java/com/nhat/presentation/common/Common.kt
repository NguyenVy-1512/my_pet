package com.nhat.presentation.common

import com.nhat.presentation.service.APIService
import com.nhat.presentation.service.ApiClient

object Common {
    private const val GoogleMapBaseUrl = "https://maps.googleapis.com/maps/api"
    val googleApiServices: APIService
        get() = ApiClient.getClient("$GoogleMapBaseUrl/").create(APIService::class.java)

    fun buildGooglePlaceApiUrl(
        keyword: String,
        latitude: Double,
        longitude: Double,
        radius: Int,
        apiKey: String
    ) = StringBuilder("$GoogleMapBaseUrl/place/nearbysearch/json")
        .append("?location=$latitude,$longitude")
        .append("&radius=$radius")
        .append("&keyword=$keyword")
        .append("&key=$apiKey")
        .toString()
}