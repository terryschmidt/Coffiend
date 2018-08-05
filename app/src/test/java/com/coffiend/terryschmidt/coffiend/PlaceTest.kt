package com.coffiend.terryschmidt.coffiend

import com.coffiend.terryschmidt.coffiend.model.Place
import com.google.gson.JsonObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class PlaceTest {
    @Test
    fun testNewInstanceHasNonNullFields() {
        val firstPlace = Place(JsonObject(), "Starbucks", JsonObject())
        assertNotNull(firstPlace)
        assertNotNull(firstPlace.geometry)
        assertNotNull(firstPlace.name)
        assertNotNull(firstPlace.opening_hours)
        assertEquals(firstPlace.name, "Starbucks")
    }
}