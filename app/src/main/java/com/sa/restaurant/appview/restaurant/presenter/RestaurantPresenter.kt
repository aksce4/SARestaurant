package com.sa.restaurant.appview.restaurant.presenter

import com.google.android.gms.maps.model.LatLng
import com.sa.restaurant.appview.restaurant.model.ResponseModelClass
import com.sa.restaurant.appview.roomdatabase.model.FavoriteRestaurantTable


interface RestaurantPresenter {

    fun getListOfLocations(responseFromParse: ResponseModelClass) :ArrayList<LatLng>

    fun getListOfFavLocations(listOfFavoriteRestaurantTable: ArrayList<FavoriteRestaurantTable>): ArrayList<LatLng>

}