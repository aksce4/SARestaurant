package com.sa.restaurant.appview.restaurant.presenter

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.sa.restaurant.appview.restaurant.GoogleApiServices
import com.sa.restaurant.appview.restaurant.adapter.RestaurantAdapter

interface RestaurantPresenter {

    fun BuildLocationreq(): LocationRequest

    fun BuildLocationCallback(googleApiServices: GoogleApiServices, context: ViewGroup, activity: Context, adapter: RestaurantAdapter): LocationCallback

    //Avail
    fun checklocationpermission(context: Activity): Boolean

    fun createClient(context: Context): GoogleApiClient
}