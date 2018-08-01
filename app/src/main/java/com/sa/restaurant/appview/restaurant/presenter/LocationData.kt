package com.sa.restaurant.appview.restaurant.presenter

import android.location.Location
import com.google.android.gms.maps.model.LatLng

interface LocationData {

    interface OnReceiveLocation {

        fun onError()

        fun getDeviceLastLocation(location: Location)

        fun receiveLocationUpdatesFun()
    }

    fun sendLocation(listener: OnReceiveLocation)

}