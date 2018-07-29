package com.sa.restaurant.appview.restaurant

import com.sa.restaurant.appview.restaurant.model.Pojo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface GoogleApiServices {

    @GET
    fun getnearbyrestaurants(@Url url: String): Call<Pojo>
}