package com.sa.restaurant.appview.location

import android.location.Location

interface GetLocation {

    interface OnReceiveLocation {

        fun onError()

        fun getDeviceLastLocation(location: Location)

        fun receiveLocationUpdatesFun()
    }

    fun sendLocation(listener: OnReceiveLocation)

}