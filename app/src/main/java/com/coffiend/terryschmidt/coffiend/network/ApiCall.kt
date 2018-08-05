package com.coffiend.terryschmidt.coffiend.network

import android.location.Location
import android.util.Log
import com.coffiend.terryschmidt.coffiend.controllers.MarkerController
import com.coffiend.terryschmidt.coffiend.model.PlaceListWrapper
import com.coffiend.terryschmidt.coffiend.views.CoffiendActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiCall(val markerController: MarkerController) {
    fun fetchDataFromAPI(location: Location, apiKey: String) {
        val service = RetrofitInstance.getRetrofitInstance()?.create(GetDataService::class.java)
        val call = service?.getCoffeeShops(location.latitude.toString() + "," + location.longitude.toString(), "distance", "cafe", "coffee", apiKey)
        call?.enqueue(object : Callback<PlaceListWrapper> {
            override fun onFailure(call: Call<PlaceListWrapper>?, t: Throwable?) {
                Log.e(CoffiendActivity.TAG, "onFailure: " + t?.message)
            }

            override fun onResponse(call: Call<PlaceListWrapper>?, response: Response<PlaceListWrapper>?) {
                Log.d(CoffiendActivity.TAG, "onResponse: " + response?.isSuccessful)
                if (response == null) { Log.e(CoffiendActivity.TAG, "null response") }
                if (response!!.body() == null) { Log.e(CoffiendActivity.TAG, "null body") }
                if (response.body()!!.placeList == null) { Log.e(CoffiendActivity.TAG, "null placeList") }
                Log.d(CoffiendActivity.TAG, "PlaceList size: " + response.body()!!.placeList.size)
                val placeList = response.body()?.placeList
                if (placeList != null) {
                    for (place in placeList) {
                        val lat: Double = place.geometry.get("location").asJsonObject.get("lat").asDouble
                        val lon: Double = place.geometry.get("location").asJsonObject.get("lng").asDouble
                        var isOpen: Boolean? = null
                        try {
                            isOpen = place.opening_hours.asJsonObject.get("open_now").asBoolean
                        } catch (e: Exception) {
                            Log.e(CoffiendActivity.TAG, "Caught exception: " + e.message)
                        }
                        markerController.addCoffeeShopLocationMarker(lat, lon, isOpen, place.name)
                    }
                }
            }
        })
    }
}