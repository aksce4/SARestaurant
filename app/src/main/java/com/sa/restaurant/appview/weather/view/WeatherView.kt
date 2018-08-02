package com.sa.restaurant.appview.weather.view

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.view.View
import com.sa.restaurant.appview.weather.WeatherApiClient

interface WeatherView {

    //fun sendlocation(location: Location, context: Context, weatherApiClient: WeatherApiClient, view: View)

    fun sendweatherInfo(bundle: Bundle, context: Context, view: View)
}