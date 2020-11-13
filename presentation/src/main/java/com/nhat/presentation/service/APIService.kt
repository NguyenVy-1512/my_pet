package com.nhat.presentation.service

import com.nhat.presentation.module.MyPlaces

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url


interface APIService {
    @GET
    fun getNearbyPlaces(@Url url: String): Call<MyPlaces?>?
}