package com.sa.restaurant.appview.restaurant

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.android.gms.maps.model.LatLng
import com.sa.restaurant.R
import com.sa.restaurant.appview.home.HomeActivity
import com.sa.restaurant.appview.restaurant.adapter.FavoriteListAdapter
import com.sa.restaurant.appview.restaurant.presenter.LocationCommunication
import com.sa.restaurant.appview.restaurant.presenter.LocationData
import com.sa.restaurant.appview.restaurant.presenter.RestaurantPresenterImp
import com.sa.restaurant.appview.roomdatabase.model.FavoriteRestaurantTable
import com.sa.restaurant.appview.roomdatabase.MyDatabase
import kotlinx.android.synthetic.main.fragment_restaurant.*


class FavoriteFragment: Fragment() {

    val TAG = "RestaurantFragment"
    lateinit var homeActivity: HomeActivity
    lateinit var userDataBase: MyDatabase
    var listOfPlacesLocation: ArrayList<LatLng> = ArrayList()
    lateinit var restaurantPresenterImp: RestaurantPresenterImp
    lateinit var locationData: LocationCommunication

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
        homeActivity.supportActionBar?.show()
        locationData = homeActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_restaurant, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        userDataBase = Room.databaseBuilder(homeActivity, MyDatabase::class.java, "AppData")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries().build()
        restaurantPresenterImp = RestaurantPresenterImp()
        Log.d(TAG, "onActivityCreated: ${userDataBase.favoriteRestaurantDao().getAll().toString()}");

        val layout = LinearLayoutManager(activity)
        listOfPlacesLocation = restaurantPresenterImp.getListOfFavLocations(userDataBase.favoriteRestaurantDao().getAll() as ArrayList<FavoriteRestaurantTable>)
        locationData.sendLocationFromRestaurant(listOfPlacesLocation)
        recyclerview.adapter = FavoriteListAdapter(userDataBase.favoriteRestaurantDao().getAll(), homeActivity, userDataBase)
        recyclerview.layoutManager = layout

    }

}