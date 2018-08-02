package com.sa.restaurant.appview.weather.presenter

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.view.View
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.sa.restaurant.appview.weather.WeatherApiClient

interface WeatherPresenter {

    fun BuildLocationreq(): LocationRequest

    fun Buildlocationcallback(weatherApiClient: WeatherApiClient, activity: Context, view: View): LocationCallback

    fun createClient(context: Context): GoogleApiClient

 //   fun getNameFromLatLng(location: Location, context: Context, weatherApiClient: WeatherApiClient, view: View)
    fun getWeatherInfo(context: Context, bundle: Bundle, weatherApiClient: WeatherApiClient, view: View)
}