package com.coffiend.terryschmidt.coffiend

import com.coffiend.terryschmidt.coffiend.model.Place
import com.coffiend.terryschmidt.coffiend.model.PlaceListWrapper
import com.google.gson.JsonObject
import org.junit.Assert
import org.junit.Test

class PlaceListWrapperTest {
    @Test
    fun testNewInstanceHasNonNullFields() {
        val placeList: MutableList<Place> = ArrayList()
        val wrapper = PlaceListWrapper(placeList)
        Assert.assertNotNull(placeList)
        Assert.assertNotNull(wrapper)
        Assert.assertNotNull(wrapper.placeList)
    }

    @Test
    fun testNewInstanceHasPlaces() {
        val firstPlace = Place(JsonObject(), "Starbucks", JsonObject())
        val secondPlace = Place(JsonObject(), "Dunkin' Donuts", JsonObject())
        val thirdPlace = Place(JsonObject(), "McDonald's", JsonObject())
        val placeList: MutableList<Place> = ArrayList()
        placeList.add(firstPlace)
        placeList.add(secondPlace)
        placeList.add(thirdPlace)
        val wrapper = PlaceListWrapper(placeList)
        Assert.assertNotNull(wrapper.placeList.get(0))
        Assert.assertEquals(wrapper.placeList.get(0).name, "Starbucks")
        Assert.assertNotNull(wrapper.placeList.get(1))
        Assert.assertEquals(wrapper.placeList.get(1).name, "Dunkin' Donuts")
        Assert.assertNotNull(wrapper.placeList.get(2))
        Assert.assertEquals(wrapper.placeList.get(2).name, "McDonald's")
    }
}