package com.nhat.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nhat.presentation.common.Common.buildGooglePlaceApiUrl
import com.nhat.presentation.model.PetShopView
import com.nhat.presentation.module.MyPlaces
import com.nhat.presentation.service.APIService
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {
    private val liveData = MutableLiveData<MutableList<PetShopView>>()

    //FIXME refactor by using DI
    private var apiKey: String = ""

    fun get(): LiveData<MutableList<PetShopView>> {
        return liveData
    }

    fun setApiKey(value: String) {
        apiKey = value
    }

    fun nearByPlace(
        keyword: String,
        mService: APIService,
        latitude: Double,
        longitude: Double,
        radius: Int
    ) {
        val listShop = mutableListOf<PetShopView>()
        val url = buildGooglePlaceApiUrl(keyword, latitude, longitude, radius, apiKey)
        mService.getNearbyPlaces(url)
            ?.enqueue(object : retrofit2.Callback<MyPlaces?> {
                override fun onResponse(call: Call<MyPlaces?>, response: Response<MyPlaces?>) {
                    if (response.isSuccessful) {
                        response.body()?.results?.filter {
                            it.geometry?.location?.let { true }
                                ?: false //Not allow null geometry location one
                        }?.forEachIndexed { index, place ->
                            val placeLocation = requireNotNull(place.geometry?.location)
                            listShop.add(
                                PetShopView(
                                    id = index,
                                    placeName = place.name,
                                    latitude = placeLocation.lat,
                                    longitude = placeLocation.lng
                                )
                            )
                        }
                        liveData.postValue(listShop)
                    } else {
                        TODO("Handle response fail - Not yet implemented")
                    }
                }

                override fun onFailure(call: Call<MyPlaces?>, t: Throwable) {
                    TODO("Handle failure - Not yet implemented")
                }
            })
    }
}
