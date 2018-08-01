package com.sa.restaurant.appview.weather


import com.sa.restaurant.appview.weather.model.Weathers
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface WeatherApiClient {

    @GET
    fun getWeatherInfo(@Url url: String): Call<Weathers>
}