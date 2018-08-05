package com.coffiend.terryschmidt.coffiend.controllers

import com.coffiend.terryschmidt.coffiend.views.CoffiendActivity
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.ref.WeakReference

class MarkerController(val activity: WeakReference<CoffiendActivity>) {

    var userLocationMarker: Marker? = null
    var coffeeShopLocationMarkers: MutableList<Marker> = ArrayList()

    fun removePreviousUserLocationMarker() {
        userLocationMarker?.remove()
    }

    fun removePreviousCoffeeShopMarkers() {
        for (marker in coffeeShopLocationMarkers) {
            marker.remove()
        }
        coffeeShopLocationMarkers.clear()
    }

    fun addUserLocationMarker(lat: Double, lon: Double) {
        val location = LatLng(lat, lon)
        userLocationMarker = activity.get()?.map?.addMarker(MarkerOptions().position(location).title("Your Location"))
    }

    fun addCoffeeShopLocationMarker(lat: Double, lon: Double, isOpen: Boolean?, name: String) {
        val location = LatLng(lat, lon)
        val openOrClosed: String = if (isOpen == null) {
            "Unknown"
        } else if (isOpen) {
            "Open"
        } else {
            "Closed"
        }
        val marker = activity.get()?.map!!.addMarker(MarkerOptions().position(location).title(name).snippet(openOrClosed))
        setMarkerColor(marker)
        coffeeShopLocationMarkers.add(marker)
    }

    fun setMarkerColor(marker: Marker) {
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
    }
}