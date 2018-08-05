package com.coffiend.terryschmidt.coffiend.network

import com.coffiend.terryschmidt.coffiend.model.PlaceListWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetDataService {
    @GET("maps/api/place/nearbysearch/json?")
    fun getCoffeeShops(
            @Query("location") location: String,
            @Query("rankby") rankby: String,
            @Query("type") type: String,
            @Query("keyword") keyword: String,
            @Query("key") key: String): Call<PlaceListWrapper>
}