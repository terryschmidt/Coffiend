package com.coffiend.terryschmidt.coffiend.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

class Place(geometry: JsonObject, name: String, opening_hours: JsonObject) {
    @SerializedName("name")
    val name: String = name

    @SerializedName("opening_hours")
    val opening_hours: JsonObject = opening_hours

    @SerializedName("geometry")
    val geometry: JsonObject = geometry
}