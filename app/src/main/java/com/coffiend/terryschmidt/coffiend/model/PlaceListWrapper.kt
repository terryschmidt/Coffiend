package com.coffiend.terryschmidt.coffiend.model

import com.google.gson.annotations.SerializedName

class PlaceListWrapper(list: List<Place>) {
    @SerializedName("results")
    var placeList: List<Place> = list
}