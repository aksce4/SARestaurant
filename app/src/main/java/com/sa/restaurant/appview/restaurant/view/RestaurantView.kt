package com.sa.restaurant.appview.restaurant.view

import android.content.Context
import android.location.Location
import android.view.ViewGroup
import com.sa.restaurant.appview.restaurant.GoogleApiServices
import com.sa.restaurant.appview.restaurant.adapter.RestaurantAdapter
import com.sa.restaurant.appview.restaurant.model.RestaurantDetails

interface RestaurantView {

    fun currentlatlng(location: Location, googleApiServices: GoogleApiServices, context: ViewGroup, activity: Context, adapter: RestaurantAdapter)

    fun restaurnatlist(list: ArrayList<RestaurantDetails>, activity: Context, adapter: RestaurantAdapter)
}