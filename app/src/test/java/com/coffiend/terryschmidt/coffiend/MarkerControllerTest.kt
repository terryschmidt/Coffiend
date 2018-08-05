package com.coffiend.terryschmidt.coffiend

import android.content.Context
import com.coffiend.terryschmidt.coffiend.controllers.MarkerController
import com.coffiend.terryschmidt.coffiend.views.CoffiendActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.lang.ref.WeakReference

class MarkerControllerTest {
    @Mock
    val mockActivity = mock(CoffiendActivity::class.java)
    @Mock
    val mockMap = mock(GoogleMap::class.java)
    @Mock
    val userLocationMarker = mock(Marker::class.java)
    @Mock
    val coffeeShopLocationMarker = mock(Marker::class.java)

    lateinit var controller: MarkerController

    @Mock
    lateinit var weakRefActivity: WeakReference<CoffiendActivity>
    @Mock
    var context = mock(Context::class.java)

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        weakRefActivity = WeakReference(mockActivity)
        controller = MarkerController(weakRefActivity)
        controller.userLocationMarker = userLocationMarker
        weakRefActivity.get()?.map = mockMap
    }

    @Test
    fun testRemovePreviousUserLocationMarker() {
        controller.removePreviousUserLocationMarker()
        verify(userLocationMarker, times(1)).remove()
    }

    @Test
    fun testRemovePreviousCoffeeShopMarkers() {
        var list: MutableList<Marker> = ArrayList()
        assertEquals(0, controller.coffeeShopLocationMarkers.size)
        list.add(coffeeShopLocationMarker)
        controller.coffeeShopLocationMarkers = list
        assertEquals(1, controller.coffeeShopLocationMarkers.size)
        controller.removePreviousCoffeeShopMarkers()
        assertEquals(0, controller.coffeeShopLocationMarkers.size)
    }

    @Test
    fun testAddUserLocationMarker() {
        val location = LatLng(25.0, 50.0)
        Assert.assertNotNull(mockActivity)
        Assert.assertNotNull(weakRefActivity.get())
        `when`(weakRefActivity.get()?.map).thenReturn(mockMap)
        Assert.assertNotNull(weakRefActivity.get()?.map)
        controller.addUserLocationMarker(location.latitude, location.longitude)
        verify(controller.activity.get()?.map, times(1))?.addMarker(ArgumentMatchers.any())
    }

    @Test
    fun testAddCoffeeShopLocationMarker() {
//        val location = LatLng(25.0, 50.0)
//        Assert.assertNotNull(mockActivity)
//        Assert.assertNotNull(weakRefActivity.get())
//        `when`(weakRefActivity.get()?.map).thenReturn(mockMap)
//        Assert.assertNotNull(weakRefActivity.get()?.map)
//        doNothing().`when`(controller.setMarkerColor(ArgumentMatchers.any()))
//        controller.addCoffeeShopLocationMarker(location.latitude, location.longitude, true, "Starbucks")
//        verify(controller.activity.get()?.map, times(1))?.addMarker(ArgumentMatchers.any())
    }
}