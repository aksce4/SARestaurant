package com.sa.restaurant.appview.location

import android.content.Context
import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest

class GetLocationImpl(var mLocationPermissionsGranted: Boolean, var mFusedLocationProviderClient: FusedLocationProviderClient, var context: Context): GetLocation {

    val TAG = "GetLocation"
    private var locationRequest: LocationRequest? = null
    private var locationCallback: LocationCallback? = null
    var currentLocation: Location? = null

    var getLocation: GetLocation? = null

    override fun sendLocation(listener: GetLocation.OnReceiveLocation) {
        try {
            if (mLocationPermissionsGranted){
                var location = mFusedLocationProviderClient.lastLocation
                location.addOnCompleteListener {
                    if(it.isSuccessful){
                        Log.d(TAG, "getDeviceLocation: found Location${it.result}")
                        currentLocation = it.result
                        listener.getDeviceLastLocation(currentLocation!!)
                    }else{
                        Log.d(TAG, "getDeviceLocation: Current location is null")
                    }
                }
            }
        }catch (e: SecurityException) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.message)
        }
    }
}

