package com.sa.restaurant.appview.restaurant.presenter

import android.content.Context
import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.LatLng

class LocationDataImpl(var mLocationPermissionsGranted: Boolean, var mFusedLocationProviderClient: FusedLocationProviderClient, var context: Context): LocationData{

    private var locationRequest: LocationRequest? = null
    private var locationCallback: LocationCallback? = null
    var currentLocation: Location? = null
    var listOfLocations: java.util.ArrayList<LatLng> = java.util.ArrayList()

    var getLocation: LocationData? = null

    override fun getLocationFromRestaurant(): ArrayList<LatLng> {
       return listOfLocations
    }

    override fun sendLocationFromRestaurant(listOfLocations: ArrayList<LatLng>) {
        this.listOfLocations = listOfLocations
    }

    override fun sendLocation(listener: LocationData.OnReceiveLocation) {
        try {
            if (mLocationPermissionsGranted){
                var location = mFusedLocationProviderClient.lastLocation
                location.addOnCompleteListener {
                    if(it.isSuccessful){
                        currentLocation = it.result
                        listener.getDeviceLastLocation(currentLocation!!)
                    }else{
                    }
                }
            }
        }catch (e: SecurityException) {
            Log.e("LocationDataImpl", "getDeviceLocation: SecurityException: " + e.message)
        }
    }
}