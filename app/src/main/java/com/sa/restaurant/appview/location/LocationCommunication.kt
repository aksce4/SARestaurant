package com.sa.restaurant.appview.location

import com.google.android.gms.maps.model.LatLng

interface LocationCommunication {

    fun sendLocationFromRestaurant(listOfLocations:ArrayList<LatLng>)

    fun getLocationFromRestaurant(): ArrayList<LatLng>
}