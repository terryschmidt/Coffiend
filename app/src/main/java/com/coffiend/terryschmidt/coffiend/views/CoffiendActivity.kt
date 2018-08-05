package com.coffiend.terryschmidt.coffiend.views

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.coffiend.terryschmidt.coffiend.R
import com.coffiend.terryschmidt.coffiend.controllers.MarkerController
import com.coffiend.terryschmidt.coffiend.network.ApiCall
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import java.lang.ref.WeakReference

class CoffiendActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var map: GoogleMap
    private val requestFineLocation = 1
    private var locationListener: LocationListener? = null
    private var locationManager: LocationManager? = null
    private lateinit var alertDialog: AlertDialog
    private lateinit var markerController: MarkerController
    private lateinit var apiCall: ApiCall

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coffiend)
        markerController = MarkerController(WeakReference(this))  // use dependency injection TODO
        apiCall = ApiCall(markerController)
        alertDialog = getAlertDialog()
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestFineLocationPermission()
        }
    }

    override fun onPause() {
        super.onPause()
        locationManager?.removeUpdates(locationListener)
        locationListener = null
        locationManager = null
    }

    override fun onResume() {
        super.onResume()
            requestLocationUpdates(0, 400f)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            requestFineLocation -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    requestLocationUpdates(0, 400f)
                }
                return
            }
            else -> { }
        }
    }

    private fun requestFineLocationPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), requestFineLocation)
    }

    private fun requestLocationUpdates(minTime: Long, minDistance: Float) {
        createLocationManager()
        createLocationListener()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (checkIfLocationServicesEnabled()) {
                locationManager?.removeUpdates(locationListener)
                locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, locationListener)
            } else {
                if (!alertDialog.isShowing) {
                    alertDialog.show()
                }
            }
        }
    }

    private fun checkIfLocationServicesEnabled(): Boolean {
        val locationSetting = Settings.Secure.getInt(contentResolver, Settings.Secure.LOCATION_MODE)
        if (locationSetting == 0) {
            return false
        }
        return true
    }

    private fun getAlertDialog(): AlertDialog {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Location Services disabled")
        builder.setMessage("Location Services must be on for this application.  Please enable.")
        builder.setPositiveButton("Enable") { dialog, which ->
            val gpsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(gpsIntent)
        }
        builder.setNegativeButton("Exit") { dialog, which ->
            dialog.dismiss()
            finish()
        }
        return builder.create()
    }

    private fun createLocationListener() {
        if (locationListener == null) {
            locationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) { // Called when a new location is found by the network location provider.
                    markerController.removePreviousCoffeeShopMarkers()
                    markerController.removePreviousUserLocationMarker()
                    showNearbyCoffeeShops(location)
                }
                override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
                override fun onProviderEnabled(provider: String) {}
                override fun onProviderDisabled(provider: String) {}
            }
        }
    }

    private fun createLocationManager() {
        if (locationManager == null) {
            locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }
    }

    private fun showNearbyCoffeeShops(location: Location?) {
        if (location != null) {
            Log.d(TAG, "Location: " + location.latitude + " " + location.longitude)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 12.0f))
            markerController.addUserLocationMarker(location.latitude, location.longitude)
            apiCall.fetchDataFromAPI(location, getString(R.string.google_maps_key))
        } else {
            Log.d(TAG, "showNearbyCoffeeShops: null location")
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }

    companion object {
        const val TAG = "CoffiendActivity"
    }
}