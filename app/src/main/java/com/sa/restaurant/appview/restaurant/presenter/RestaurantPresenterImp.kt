package com.sa.restaurant.appview.restaurant.presenter

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.sa.restaurant.appview.restaurant.model.ResponseModelClass
import com.sa.restaurant.appview.roomdatabase.model.FavoriteRestaurantTable

class RestaurantPresenterImp: RestaurantPresenter {

    var listOfLocations: ArrayList<LatLng> = ArrayList()
    var listOfFavLocations: ArrayList<LatLng> = ArrayList()

    override fun getListOfLocations(responseFromParse: ResponseModelClass): ArrayList<LatLng> {

        for(i in responseFromParse.results!!){
            listOfLocations.add(LatLng(i.geometry?.location?.lat!!, i.geometry?.location?.lng!!))
        }

        return listOfLocations
    }

    override fun getListOfFavLocations(listOfFavoriteRestaurantTable: ArrayList<FavoriteRestaurantTable>): ArrayList<LatLng> {

        Log.d("RestaurantPresenterImp", "getListOfFavLocations: ${listOfFavoriteRestaurantTable.size}");

        for(i in listOfFavoriteRestaurantTable){
            listOfFavLocations.add(LatLng(i.latitude!!, i.longitude!!))
        }

        return listOfFavLocations

    }

}